package fr.inria.jtravis.entities;

public class JobStub {
    public static Job failingJob() {
        int jobId = 212676600;
        Job job = new Job();
        job.setId(jobId);
        job.setState(StateType.FAILED);
        return job;
    }

    public static Job erroringJob() {
        int jobId = 210935305;
        Job job = new Job();
        job.setId(jobId);
        job.setState(StateType.ERRORED);
        return job;
    }

    public static Job passingJob() {
        Job job = new Job();
        job.setId(185719844);
        job.setState(StateType.PASSED);
        return job;
    }
}
