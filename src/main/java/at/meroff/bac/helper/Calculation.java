package at.meroff.bac.helper;

import at.meroff.bac.domain.Card;
import com.sun.javafx.geom.Vec2d;

public class Calculation {

    /**
     * Distance between the subject and the source task
     */
    double distanceSubjectSource;
    /**
     * Distance between the subject and the target task
     */
    double distanceSubjectTarget;
    /**
     * Distance between the source task and the target task
     */
    double distanceSourceTarget;
    /**
     * the maximum cosine similarity (for determination if the target is vaild)
     */
    double maxSimilarity;
    /**
     * the cosine similarity between source task and target task in relation to the subject
     */
    double similarity;
    /**
     * cosine similarity between Subject/Source and Source/Target
     */
    double similarityFromSource;

    //TODO ev. auch die zweite Kosinus Ähnlichkeit gleich mitberücksichtigen
    boolean isValidSimilarity;

    /**
     * Constructor
     * @param subject the base subject
     * @param sourceTask the base task
     * @param targetTask the follow-up task
     */
    public Calculation(Card subject, Card sourceTask, Card targetTask) {

        this.distanceSubjectSource = Card.getDistance(subject, sourceTask);

        this.distanceSubjectTarget = Card.getDistance(subject, targetTask);

        this.distanceSourceTarget = Card.getDistance(sourceTask, targetTask);

        this.maxSimilarity = Card.getMaxCosineSimilarity(subject, sourceTask);

        Vec2d vSubjectSource = Card.getVector(subject, sourceTask);
        Vec2d vSubjectTarget = Card.getVector(subject, targetTask);
        this.similarity = Cosine.similarity(vSubjectSource, vSubjectTarget);

        Vec2d vSourceTarget = Card.getVector(sourceTask, targetTask);
        this.similarityFromSource = Cosine.similarity(vSubjectSource, vSourceTarget);

        isValidSimilarity = this.maxSimilarity < similarity;
    }

    @Override
    public String toString() {
        return "Calculation{" +
                "maxSimilarity=" + maxSimilarity +
                ", similarity=" + similarity +
                ", isValidSimilarity=" + isValidSimilarity +
                '}';
    }
}
