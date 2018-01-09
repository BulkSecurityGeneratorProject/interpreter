package at.meroff.bac.service;

import at.meroff.bac.domain.Card;
import at.meroff.bac.domain.Connection;
import at.meroff.bac.domain.Field;
import at.meroff.bac.domain.enumeration.CardType;
import at.meroff.bac.repository.CardRepository;
import at.meroff.bac.repository.ConnectionRepository;
import at.meroff.bac.repository.FieldRepository;
import at.meroff.bac.service.dto.FieldDTO;
import at.meroff.bac.service.dto.FieldDTOSmall;
import at.meroff.bac.service.mapper.FieldMapper;
import at.meroff.bac.service.mapper.FieldSmallMapper;
import jdk.nashorn.internal.runtime.regexp.joni.constants.NodeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Field.
 */
@Service
@Transactional
public class FieldService {

    private final Logger log = LoggerFactory.getLogger(FieldService.class);

    private final FieldRepository fieldRepository;

    private final CardRepository cardRepository;

    private final FieldMapper fieldMapper;

    private final FieldSmallMapper fieldSmallMapper;

    private final ConnectionRepository connectionRepository;

    public FieldService(FieldRepository fieldRepository, CardRepository cardRepository, ConnectionRepository connectionRepository, FieldMapper fieldMapper, FieldSmallMapper fieldSmallMapper) {
        this.fieldRepository = fieldRepository;
        this.fieldMapper = fieldMapper;
        this.cardRepository = cardRepository;
        this.fieldSmallMapper = fieldSmallMapper;
        this.connectionRepository = connectionRepository;
    }

    /**
     * Save a field.
     *
     * @param fieldDTO the entity to save
     * @return the persisted entity
     */
    public FieldDTO save(FieldDTO fieldDTO) {
        log.debug("Request to save Field : {}", fieldDTO);

        Field field = fieldMapper.toEntity(fieldDTO);

        if (fieldDTO.getId() == null) {
            log.debug("Save a new Field");
            // Create cards from svg

            try {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(new ByteArrayInputStream(field.getSvgImage()));
                doc.getDocumentElement().normalize();

                // find all nodes by tag name (cards)
                NodeList nList = doc.getElementsByTagName("path");

                for (int i = 0; i < nList.getLength(); i++) {
                    Node nNode = nList.item(i);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;

                        if (eElement.hasAttribute("class") && eElement.getAttribute("class").equals("karterl")) {
                            String[] coordinates = eElement.getAttribute("d").replaceAll("L", "").replaceAll("M", "").split(" ");
                            coordinates = Arrays.copyOfRange(coordinates, 2, 10);
                            Card card = new Card();
                            card.setCardId(Integer.parseInt(((Element) eElement.getParentNode()).getAttribute("id")));
                            card.setCardType(CardType.values()[Integer.parseInt(((Element) eElement.getParentNode()).getAttribute("id")) % 3]);
                            card.setx1(Double.parseDouble(coordinates[0]));
                            card.sety1(Double.parseDouble(coordinates[1]));
                            card.setx2(Double.parseDouble(coordinates[2]));
                            card.sety2(Double.parseDouble(coordinates[3]));
                            card.setx3(Double.parseDouble(coordinates[4]));
                            card.sety3(Double.parseDouble(coordinates[5]));
                            card.setx4(Double.parseDouble(coordinates[6]));
                            card.sety4(Double.parseDouble(coordinates[7]));
                            card.setDescription("something");
                            card = cardRepository.save(card);
                            field.addCard(card);
                        }

                    }
                }

            } catch (ParserConfigurationException | IOException | SAXException e) {
                e.printStackTrace();
            }

            try {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(new ByteArrayInputStream(field.getXmlData()));
                doc.getDocumentElement().normalize();

                // find all nodes by tag name (cards)
                NodeList nList = doc.getElementsByTagName("Connection");

                for (int i = 0; i < nList.getLength(); i++) {

                    Connection connection = new Connection();

                    Node nNode = nList.item(i);

                    NodeList childNodes = nNode.getChildNodes();

                    for (int j = 0; j <childNodes.getLength(); j++) {
                        Node item = childNodes.item(j);

                        if (item.getNodeType() == Node.ELEMENT_NODE) {
                            System.out.println(item.getNodeName());
                            System.out.println(item.getNodeType());
                            if (item.getNodeName().equalsIgnoreCase("uuid")) {
                                connection.setUuid(Integer.parseInt(item.getTextContent()));
                            } else if (item.getNodeName().equalsIgnoreCase("name")) {
                                connection.setName(item.getTextContent());
                            } else if (item.getNodeName().equalsIgnoreCase("directed1")) {
                                if (item.getTextContent().equalsIgnoreCase("true")) connection.setDirected1(true); else connection.setDirected1(false);
                            } else if (item.getNodeName().equalsIgnoreCase("directed2")) {
                                if (item.getTextContent().equalsIgnoreCase("true")) connection.setDirected2(true); else connection.setDirected2(false);
                            } else if (item.getNodeName().equalsIgnoreCase("endPoint1")) {
                                NodeList childNodes1 = item.getChildNodes();
                                for (int k = 0; k < childNodes1.getLength(); k++) {
                                    Node point = childNodes1.item(k);
                                    if (point.getNodeType() == Node.ELEMENT_NODE) {
                                        if (point.getNodeName().equalsIgnoreCase("uuid")) {
                                            connection.setEndPoint1Uuid(Integer.parseInt(point.getTextContent()));
                                        } else if (point.getNodeName().equalsIgnoreCase("x")) {
                                            connection.setEndPoint1X(Double.parseDouble(point.getTextContent()));
                                        } else if (point.getNodeName().equalsIgnoreCase("y")) {
                                            connection.setEndPoint1Y(Double.parseDouble(point.getTextContent()));
                                        } else if (point.getNodeName().equalsIgnoreCase("angle")) {
                                            connection.setEndPoint1Angle(Double.parseDouble(point.getTextContent()));
                                        }
                                    }

                                }
                            } else if (item.getNodeName().equalsIgnoreCase("endPoint2")) {
                                NodeList childNodes1 = item.getChildNodes();
                                for (int k = 0; k < childNodes1.getLength(); k++) {
                                    Node point = childNodes1.item(k);
                                    if (point.getNodeType() == Node.ELEMENT_NODE) {
                                        if (point.getNodeName().equalsIgnoreCase("uuid")) {
                                            connection.setEndPoint2Uuid(Integer.parseInt(point.getTextContent()));
                                        } else if (point.getNodeName().equalsIgnoreCase("x")) {
                                            connection.setEndPoint2X(Double.parseDouble(point.getTextContent()));
                                        } else if (point.getNodeName().equalsIgnoreCase("y")) {
                                            connection.setEndPoint2Y(Double.parseDouble(point.getTextContent()));
                                        } else if (point.getNodeName().equalsIgnoreCase("angle")) {
                                            connection.setEndPoint2Angle(Double.parseDouble(point.getTextContent()));
                                        }
                                    }

                                }
                            }
                        }



                    }
                    connection = connectionRepository.save(connection);
                    field.addConnection(connection);

                    connectionRepository.save(field.getConnections());

                }

                field.findRelations();

                cardRepository.save(field.getCards());
                connectionRepository.save(field.getConnections());

            } catch (ParserConfigurationException | IOException | SAXException e) {
                e.printStackTrace();
            }

        }

        field = fieldRepository.save(field);
        return fieldMapper.toDto(field);
    }

    /**
     * Get all the fields.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public LinkedList<FieldDTOSmall> findAll() {
        log.debug("Request to get all Fields");
        return fieldRepository.findAll().stream()
            .map(field -> fieldSmallMapper.toDto(field))
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one field by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public FieldDTO findOne(Long id) {
        log.debug("Request to get Field : {}", id);
        Field field = fieldRepository.findOne(id);
        return fieldMapper.toDto(field);
    }

    /**
     * Delete the field by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Field : {}", id);
        fieldRepository.delete(id);
    }
}
