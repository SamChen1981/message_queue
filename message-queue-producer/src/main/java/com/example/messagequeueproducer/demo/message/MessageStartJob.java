package com.example.messagequeueproducer.demo.message;

import com.github.ltsopensource.core.domain.Action;
import com.github.ltsopensource.core.domain.Job;
import com.github.ltsopensource.jobclient.JobClient;
import com.github.ltsopensource.spring.tasktracker.JobRunnerItem;
import com.github.ltsopensource.spring.tasktracker.LTS;
import com.github.ltsopensource.tasktracker.Result;
import com.github.ltsopensource.tasktracker.runner.JobContext;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author winters
 * 创建时间：27/12/2017 12:46
 * 创建原因：
 **/
@Component
@LTS
public class MessageStartJob implements CommandLineRunner {

    private final ObjectProvider<JobClient> clientObjectProvider;

    private final MessageSendService messageSendService;

    @Value("${message-queue.task-id}")
    private String taskId;

    @Autowired
    public MessageStartJob(ObjectProvider<JobClient> clientObjectProvider, MessageSendService messageSendService) {
        this.clientObjectProvider = clientObjectProvider;
        this.messageSendService = messageSendService;
    }

    /**
     * Callback used to run the bean.
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {
        Job job = new Job();
        job.setTaskId(taskId);
        //已经存在任务 则更新这个任务 多个实例启动 最后一个实例的任务会替换掉之前的
        job.setReplaceOnExist(true);
        job.setCronExpression("0/5 * * * * ?");
        job.setTaskTrackerNodeGroup("nec-server");
        job.setSubmitNodeGroup("nec-server");
        job.setParam("type", "message-queue");
        JobClient jobClient = this.clientObjectProvider.getIfAvailable();
        if (jobClient != null) {
            jobClient.submitJob(job);
        }
    }

    @JobRunnerItem(shardValue = "message-queue")
    public Result handleMessageCronJob(JobContext jobContext) {
        messageSendService.sendMessage();
        return new Result(Action.EXECUTE_SUCCESS, "任务执行成功。taskId:" + jobContext.getJob().getTaskId());
    }
}
