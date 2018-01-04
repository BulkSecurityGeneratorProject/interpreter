package at.meroff.bac.domain;

import at.meroff.bac.domain.enumeration.CardType;
import at.meroff.bac.helper.Calculation;
import at.meroff.bac.helper.Statistics;

import javafx.util.Pair;
import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.apache.xml.security.utils.XMLUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.awt.geom.Line2D;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import at.meroff.bac.domain.enumeration.LayoutType;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * A Field.
 */
@Entity
@Table(name = "field")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Field implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "orig_image")
    private byte[] origImage;

    @Column(name = "orig_image_content_type")
    private String origImageContentType;

    @Lob
    @Column(name = "svg_image")
    private byte[] svgImage;

    @Column(name = "svg_image_content_type")
    private String svgImageContentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "layout_type")
    private LayoutType layoutType;

    @OneToMany(mappedBy = "field")
    //@JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Card> cards = new HashSet<>();

    @Transient
    private Set<Pair<Card, Set<Pair<Card, Set<Pair<Card, Calculation>>>>>> preCalculatedValues;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Field description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getOrigImage() {
        return origImage;
    }

    public Field origImage(byte[] origImage) {
        this.origImage = origImage;
        return this;
    }

    public void setOrigImage(byte[] origImage) {
        this.origImage = origImage;
    }

    public String getOrigImageContentType() {
        return origImageContentType;
    }

    public Field origImageContentType(String origImageContentType) {
        this.origImageContentType = origImageContentType;
        return this;
    }

    public void setOrigImageContentType(String origImageContentType) {
        this.origImageContentType = origImageContentType;
    }

    public byte[] getSvgImage() {
        return svgImage;
    }

    public Field svgImage(byte[] svgImage) {
        this.svgImage = svgImage;
        return this;
    }

    public void setSvgImage(byte[] svgImage) {
        this.svgImage = svgImage;
    }

    public String getSvgImageContentType() {
        return svgImageContentType;
    }

    public Field svgImageContentType(String svgImageContentType) {
        this.svgImageContentType = svgImageContentType;
        return this;
    }

    public void setSvgImageContentType(String svgImageContentType) {
        this.svgImageContentType = svgImageContentType;
    }

    public LayoutType getLayoutType() {
        return layoutType;
    }

    public Field layoutType(LayoutType layoutType) {
        this.layoutType = layoutType;
        return this;
    }

    public void setLayoutType(LayoutType layoutType) {
        this.layoutType = layoutType;
    }

    public Set<Card> getCards() {
        return cards;
    }

    public Field cards(Set<Card> cards) {
        this.cards = cards;
        return this;
    }

    public Field addCard(Card card) {
        this.cards.add(card);
        card.setField(this);
        return this;
    }

    public Field removeCard(Card card) {
        this.cards.remove(card);
        card.setField(null);
        return this;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Field field = (Field) o;
        if (field.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), field.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Field{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", origImage='" + getOrigImage() + "'" +
            ", origImageContentType='" + getOrigImageContentType() + "'" +
            ", svgImage='" + getSvgImage() + "'" +
            ", svgImageContentType='" + getSvgImageContentType() + "'" +
            ", layoutType='" + getLayoutType() + "'" +
            "}";
    }

    public void findRelations() {
        if (checkForStarLayout()) {
            setLayoutType(LayoutType.STAR);
        } else {
            setLayoutType(LayoutType.DEFAULT);

            // Reset task assignment
            getCards().forEach(card -> card.setSubject(null));
            getCards().forEach(card -> card.getTasks().clear());
        }

        preCalculateValues();

        while (hasNotProceededSubjects() && hasNotProceededTasks()) {
            Pair<Card, Card> firstSubjectTaskRelation = findFirstSubjectTaskRelation();

            while (firstSubjectTaskRelation != null) {
                // find follow-up tasks
                Set<Pair<Card, Calculation>> followUpTasks = findFollowUpTask(firstSubjectTaskRelation);
                followUpTasks = followUpTasks.stream()
                    //.filter(taskCalculationPair -> savedTasks.get(savedTasks.indexOf(taskCalculationPair.getKey())).getSubject() == null)
                    .filter(taskCalculationPair -> cards.stream().filter(card -> card.getCardType().equals(CardType.TASK)).filter(card -> Objects.isNull(card.getSubject())).count()>0)
                    .filter(taskCalculationPair -> Objects.isNull(taskCalculationPair.getKey().getSubject()))
                    //.filter(taskCalculationPair -> taskCalculationPair.getKey().proceeded == false)
                    .collect(Collectors.toSet());

                // filter follow-ups
                Pair<Card, Card> finalFirstSubjectTaskRelation = firstSubjectTaskRelation;
                Optional<Pair<Card, Calculation>> followUp = followUpTasks.stream()
                    .filter(taskCalculationPair -> taskCalculationPair.getValue().isValidSimilarity)
                    .filter(taskCalculationPair -> taskCalculationPair.getValue().distanceSubjectTarget - taskCalculationPair.getValue().distanceSubjectSource > 0)
                    .peek(taskCalculationPair -> {
                        if (taskCalculationPair.getValue().similarityFromSource < 0.5) System.out.println("filtered because its not in line: " + taskCalculationPair.getKey()) ;
                    })
                    .filter(taskCalculationPair -> taskCalculationPair.getValue().similarityFromSource > 0.5)
                    .filter(taskCalculationPair -> !checkForIntersectionWithSubject(finalFirstSubjectTaskRelation.getKey(), taskCalculationPair.getKey()))
                    .sorted(Comparator.comparingDouble(o -> o.getValue().distanceSubjectTarget))
                    .findFirst();

                //savedSubjects.get(savedSubjects.indexOf(firstSubjectTaskRelation.getKey())).addTask(firstSubjectTaskRelation.getValue());
                firstSubjectTaskRelation.getKey().addTasks(firstSubjectTaskRelation.getValue());
                //savedTasks.get(savedTasks.indexOf(firstSubjectTaskRelation.getValue())).setAssignedTo(firstSubjectTaskRelation.getKey());
                firstSubjectTaskRelation.getValue().setSubject(firstSubjectTaskRelation.getKey());

                if (followUp.isPresent()) {
                    Pair<Card, Calculation> taskCalculationPair = followUp.get();
                    firstSubjectTaskRelation = new Pair<>(firstSubjectTaskRelation.getKey(), taskCalculationPair.getKey());
                } else {
                    firstSubjectTaskRelation = null;
                }
            }
        }

        if (cards.stream().filter(card -> card.getCardType().equals(CardType.SUBJECT)).filter(card -> card.getTasks().size() == 0).count() > 0) {
            // there are subjects without assigned tasks
            System.out.println("Fixing Subjects");

            // reassign tasks for unused subjects
            cards.stream().filter(card -> card.getCardType().equals(CardType.SUBJECT)).filter(card -> card.getTasks().size() == 0)
                .forEach(subject -> {
                    Card closestTask = findClosestTask(subject);
                    closestTask.getSubject().removeTasks(closestTask);
                    subject.addTasks(closestTask);
                    closestTask.setSubject(subject);
                });
        }

        // TODO was ist mit nicht zugeordneten Tasks

        for (int i = 0; i < 100; i++) {
            checkInReverseOrder();
        }

        // create image

        DOMImplementation impl = SVGDOMImplementation.getDOMImplementation();
        String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
        Document doc = impl.createDocument(svgNS, "svg", null);

        // get the root element (the svg element)
        Element svgRoot = doc.getDocumentElement();

// set the width and height attribute on the root svg element
        svgRoot.setAttributeNS(null, "width", "4000");
        svgRoot.setAttributeNS(null, "height", "4500");

// create the rectangle


        cards.stream().filter(card -> card.getCardType().equals(CardType.SUBJECT))
            .forEach(card -> {
                Element rectangle = doc.createElementNS(svgNS, "rect");

                rectangle.setAttributeNS(null, "x", Integer.toString(card.getSmallestX()/2));
                rectangle.setAttributeNS(null, "y", Integer.toString(card.getSmallestY()/2));
                rectangle.setAttributeNS(null, "width", Integer.toString(card.getBiggestX()/2-card.getSmallestX()/2));
                rectangle.setAttributeNS(null, "height", Integer.toString(card.getBiggestY()/2-card.getSmallestY()/2));
                rectangle.setAttributeNS(null, "style", "fill:blue");
                svgRoot.appendChild(rectangle);

                Element text = doc.createElementNS(svgNS,"text");
                text.setAttributeNS(null, "x", Integer.toString((((int) card.getCenter().getX())/2)));
                text.setAttributeNS(null, "y", Integer.toString((((int) card.getCenter().getY())/2)));
                text.setAttributeNS(null, "style", "fill: #ffffff; stroke: none; font-size: 44px;");
                text.setTextContent(card.getCardId().toString());
                svgRoot.appendChild(text);

            });

        cards.stream().filter(card -> card.getCardType().equals(CardType.TASK))
            .forEach(card -> {
                Element rectangle = doc.createElementNS(svgNS, "rect");

                rectangle.setAttributeNS(null, "x", Integer.toString(card.getSmallestX()/2));
                rectangle.setAttributeNS(null, "y", Integer.toString(card.getSmallestY()/2));
                rectangle.setAttributeNS(null, "width", Integer.toString(card.getBiggestX()/2-card.getSmallestX()/2));
                rectangle.setAttributeNS(null, "height", Integer.toString(card.getBiggestY()/2-card.getSmallestY()/2));
                rectangle.setAttributeNS(null, "style", "fill:red");
                svgRoot.appendChild(rectangle);

                Element text = doc.createElementNS(svgNS,"text");
                text.setAttributeNS(null, "x", Integer.toString((((int) card.getCenter().getX())/2)));
                text.setAttributeNS(null, "y", Integer.toString((((int) card.getCenter().getY())/2)));
                text.setAttributeNS(null, "style", "fill: #ffffff; stroke: none; font-size: 44px;");
                text.setTextContent(card.getCardId().toString());
                svgRoot.appendChild(text);
            });

        cards.stream().filter(card -> card.getCardType().equals(CardType.TRANSFER))
            .forEach(card -> {
                Element rectangle = doc.createElementNS(svgNS, "rect");

                rectangle.setAttributeNS(null, "x", Integer.toString(card.getSmallestX()/2));
                rectangle.setAttributeNS(null, "y", Integer.toString(card.getSmallestY()/2));
                rectangle.setAttributeNS(null, "width", Integer.toString(card.getBiggestX()/2-card.getSmallestX()/2));
                rectangle.setAttributeNS(null, "height", Integer.toString(card.getBiggestY()/2-card.getSmallestY()/2));
                rectangle.setAttributeNS(null, "style", "fill:yellow");
                svgRoot.appendChild(rectangle);

                Element text = doc.createElementNS(svgNS,"text");
                text.setAttributeNS(null, "x", Integer.toString((((int) card.getCenter().getX())/2)));
                text.setAttributeNS(null, "y", Integer.toString((((int) card.getCenter().getY())/2)));
                text.setAttributeNS(null, "style", "fill: #ffffff; stroke: none; font-size: 44px;");
                text.setTextContent(card.getCardId().toString());
                svgRoot.appendChild(text);
            });

        cards.stream().filter(card -> card.getCardType().equals(CardType.SUBJECT))
            .forEach(card -> {
                // <line x1="0" y1="0" x2="200" y2="200" style="stroke:rgb(255,0,0);stroke-width:2" />
                for (Card card1 : card.getTasks()) {
                    if (card.getTasks().indexOf(card1) == 0) {
                        Element line = doc.createElementNS(svgNS, "line");
                        line.setAttributeNS(null, "x1", Integer.toString((int) card.getCenter().getX()/2));
                        line.setAttributeNS(null, "x2", Integer.toString((int) card1.getCenter().getX()/2));
                        line.setAttributeNS(null, "y1", Integer.toString((int) card.getCenter().getY()/2));
                        line.setAttributeNS(null, "y2", Integer.toString((int) card1.getCenter().getY()/2));
                        line.setAttributeNS(null, "style", "stroke:rgb(0,200,0);stroke-width:5");
                        svgRoot.appendChild(line);
                    } else {
                        Card cardPrev = card.getTasks().get(card.getTasks().indexOf(card1) - 1);
                        Element line = doc.createElementNS(svgNS, "line");
                        line.setAttributeNS(null, "x1", Integer.toString((int) card1.getCenter().getX()/2));
                        line.setAttributeNS(null, "x2", Integer.toString((int) cardPrev.getCenter().getX()/2));
                        line.setAttributeNS(null, "y1", Integer.toString((int) card1.getCenter().getY()/2));
                        line.setAttributeNS(null, "y2", Integer.toString((int) cardPrev.getCenter().getY()/2));
                        line.setAttributeNS(null, "style", "stroke:rgb(0,200,0);stroke-width:5");
                        svgRoot.appendChild(line);
                    }
                }


            });


// attach the rectangle to the svg root element


        DOMSource domSource = new DOMSource(doc);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = tf.newTransformer();
            transformer.transform(domSource, result);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        //System.out.println("XML IN String format is: \n" + writer.toString());
        //System.out.println(Arrays.toString(Base64.getEncoder().encode(writer.toString().getBytes())));

        setSvgImage(writer.toString().getBytes());
    }


    private void checkInReverseOrder() {

        //if (savedSubjects.size() >= savedTasks.size()) return;

        if (cards.stream().filter(card -> card.getCardType().equals(CardType.SUBJECT)).count() >= cards.stream().filter(card -> card.getCardType().equals(CardType.TASK)).count()) return;

        cards.stream().filter(card -> card.getCardType().equals(CardType.SUBJECT))
            .forEach(subject -> {
                Set<Pair<Card, Calculation>> followUpTasks = findFollowUpTask(new Pair<>(subject, subject.getTasks().get(subject.getTasks().size() - 1)));

                Optional<Pair<Card, Calculation>> followUp = followUpTasks.stream()
                    .filter(taskCalculationPair -> taskCalculationPair.getValue().isValidSimilarity)
                    .filter(taskCalculationPair -> taskCalculationPair.getValue().distanceSubjectTarget - taskCalculationPair.getValue().distanceSubjectSource > 0)
                    .peek(taskCalculationPair -> {
                        if (taskCalculationPair.getValue().similarityFromSource < 0.5)
                            System.out.println("filtered because its not in line: " + taskCalculationPair.getKey());
                    })
                    .filter(taskCalculationPair -> taskCalculationPair.getValue().similarityFromSource > 0.5)
                    .filter(taskCalculationPair -> !checkForIntersectionWithSubject(subject, taskCalculationPair.getKey()))
                    .sorted(Comparator.comparingDouble(o -> o.getValue().distanceSubjectTarget))
                    .findFirst();

                if (followUp.isPresent()) {
                    // Task which should be checked
                    Card contested = followUp.get().getKey();
                    Card currentlyAssignedSubject = contested.getSubject();
                    //System.out.println("\tTask " + contested.id + " currently assigned to: " + currentlyAssignedSubject.id);

                    if (currentlyAssignedSubject.getTasks().size() == 1) {
                        //System.out.println("\t!!! cannot move task because it is the only task for its current subject");
                    } else if ( currentlyAssignedSubject.getTasks().indexOf(contested) == 0) {
                        //System.out.println("\t!!! cannot move task because it is the only task for its the starting task for its subject");
                    } else if ( currentlyAssignedSubject.getTasks().indexOf(contested) == currentlyAssignedSubject.getTasks().size() - 1){
                        //System.out.println("\tcontesting last task");

                        //double distanceOriginal = Card.getDistance(savedSubjects.get(savedSubjects.indexOf(currentlyAssignedSubject)).assignedTasks.get(savedSubjects.get(savedSubjects.indexOf(currentlyAssignedSubject)).assignedTasks.indexOf(contested) - 1), contested);
                        double distanceOriginal = Card.getDistance(currentlyAssignedSubject.getTasks().get(currentlyAssignedSubject.getTasks().size()-2), contested);
                        double contender = Card.getDistance(subject.getTasks().get(subject.getTasks().size()-1), contested);

                        if (distanceOriginal < contender) {
                            // Do nothing
                        } else {
                            contested.getSubject().removeTasks(contested);
                            subject.addTasks(contested);
                            contested.setSubject(subject);
                        }
                    }
                }
            });



    }

    private Card findClosestTask(Card subject) {
        return cards.stream()
            .filter(card -> card.getCardType().equals(CardType.TASK))
            .map(task -> new Pair<>(task, Card.getDistance(subject, task)))
            .min(Comparator.comparingDouble(Pair::getValue))
            .map(Pair::getKey)
            .orElseThrow(() -> new IllegalStateException("blabla"));
    }

    private boolean checkForIntersectionWithSubject(Card baseSubject, Card task) {
        Line2D inter = new Line2D.Double(baseSubject.getCenter().x, baseSubject.getCenter().y, task.getCenter().x, task.getCenter().y);

        for (Card subject : cards.stream().filter(card -> card.getCardType().equals(CardType.SUBJECT)).collect(Collectors.toSet())) {
            if (!subject.equals(baseSubject)) {
                Line2D l1 = new Line2D.Double(subject.getx1(), subject.gety1(), subject.getx2(), subject.gety2());
                Line2D l2 = new Line2D.Double(subject.getx2(), subject.gety2(), subject.getx3(), subject.gety3());
                Line2D l3 = new Line2D.Double(subject.getx3(), subject.gety3(), subject.getx4(), subject.gety4());
                Line2D l4 = new Line2D.Double(subject.getx4(), subject.gety4(), subject.getx1(), subject.gety1());

                if (l1.intersectsLine(inter) || l2.intersectsLine(inter) || l3.intersectsLine(inter) || l4.intersectsLine(inter)) {
                    return true;
                }
            }
        }

        return false;
    }

    private Set<Pair<Card, Calculation>> findFollowUpTask(Pair<Card, Card> subjectTaskRelation) {
        return preCalculatedValues.stream()
            .filter(subjectSetPair -> subjectSetPair.getKey().equals(subjectTaskRelation.getKey()))
            .map(subjectSetPair -> {
                return subjectSetPair
                    .getValue()
                    .stream()
                    .filter(taskSetPair -> taskSetPair.getKey().equals(subjectTaskRelation.getValue()))
                    .findFirst()
                    .map(Pair::getValue)
                    .orElseThrow(() -> new IllegalStateException("did not found the requested task"));
            })
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("did not found the requested task"));
    }

    private Pair<Card,Card> findFirstSubjectTaskRelation() {
        return cards.stream().filter(card -> card.getCardType().equals(CardType.SUBJECT)).filter(card -> card.getTasks().size() == 0)
            .map(subject -> new Pair<>(subject,cards.stream().filter(card -> card.getCardType().equals(CardType.TASK)).filter(card -> Objects.isNull(card.getSubject()))
                .map(task -> new Pair<>(task,Card.getDistance(subject, task)))
                .min(Comparator.comparingDouble(Pair::getValue))
                .orElseThrow(() -> new IllegalStateException("no minimum found"))))
            .sorted(Comparator.comparingDouble(o -> o.getValue().getValue()))
            .map(subjectPairPair -> new Pair<>(subjectPairPair.getKey(), subjectPairPair.getValue().getKey()))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("no subject found"));
    }

    public boolean hasNotProceededSubjects() {
        long count = cards.stream()
            .filter(card -> card.getCardType().equals(CardType.SUBJECT))
            .filter(card -> card.getTasks().size() == 0)
            .count();

        return count > 0;
    }

    public boolean hasNotProceededTasks() {
        long count = cards.stream()
            .filter(card -> card.getCardType().equals(CardType.TASK))
            .filter(card -> Objects.isNull(card.getSubject()))
            .count();

        return count > 0;
    }

    private boolean checkForStarLayout() {
        Set<Card> subjects = new HashSet<>(
            this.cards.stream()
                .filter(card -> card.getCardType().equals(CardType.SUBJECT)).collect(Collectors.toSet())
        );
        Set<Card> tasks = new HashSet<>(
            this.cards.stream()
                .filter(card -> card.getCardType().equals(CardType.TASK)).collect(Collectors.toSet())
        );

        Set<Pair<Card, Set<Card>>> summary = subjects.stream()
            .map(subject -> {
                return new Pair<>(subject, tasks.stream()
                    .map(task -> {
                        return new Pair<>(task, subjects.stream()
                            .map(subject1 -> new Pair<>(subject1, Card.getDistance(task, subject1)))
                            .min(Comparator.comparingDouble(Pair::getValue))
                            .map(Pair::getKey)
                            .orElseThrow(() -> new IllegalStateException("blabla")));
                    }).filter(taskSubjectPair -> taskSubjectPair.getValue().equals(subject))
                    .map(Pair::getKey)
                    .collect(Collectors.toSet()));
            }).collect(Collectors.toSet());

        List<Card> ret = summary.stream()
            .map(subjectSetPair -> {
                Card key = subjectSetPair.getKey();
                subjectSetPair.getValue().stream()
                    .sorted(Comparator.comparingDouble(value -> Card.getDistance(key,value)))
                    .forEach(task -> {
                        key.getTasks().add(task);
                        task.setSubject(key);
                    });
                return key;
            }).collect(Collectors.toList());


        OptionalDouble average = summary.stream()
            .filter(subjectSetPair -> subjectSetPair.getValue().size() > 1)
            .map(subjectSetPair -> {
                double[] doubles = subjectSetPair.getValue().stream()
                    .mapToDouble(value -> Card.getDistance(subjectSetPair.getKey(), value)).toArray();
                Statistics statistics = new Statistics(doubles);
                return statistics.bbb();
            }).mapToDouble(value -> value)
            .average();
        System.out.println("Variationskoeffizient: " + average);

        if (average.isPresent()) {
            if (average.getAsDouble() < 0.3) {
                System.out.println("Sternmuster entdeckt!!!");
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    private void preCalculateValues() {
        this.preCalculatedValues = cards.stream()
            .filter(card -> card.getCardType().equals(CardType.SUBJECT))
            .map(subject -> new Pair<>(subject, cards.stream()
                .filter(card -> card.getCardType().equals(CardType.TASK))
                .map(sourceTask -> new Pair<>(sourceTask, cards.stream()
                    .filter(card -> card.getCardType().equals(CardType.TASK))
                    .map(targetTask -> new Pair<>(targetTask, new Calculation(subject, sourceTask, targetTask)))
                    .collect(Collectors.toSet()))
                ).collect(Collectors.toSet())))
            .collect(Collectors.toSet());
    }
}
