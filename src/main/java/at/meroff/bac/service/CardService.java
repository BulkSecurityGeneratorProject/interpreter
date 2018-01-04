package at.meroff.bac.service;

import at.meroff.bac.domain.Card;
import at.meroff.bac.repository.CardRepository;
import at.meroff.bac.service.dto.CardDTO;
import at.meroff.bac.service.mapper.CardMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Card.
 */
@Service
@Transactional
public class CardService {

    private final Logger log = LoggerFactory.getLogger(CardService.class);

    private final CardRepository cardRepository;

    private final CardMapper cardMapper;

    public CardService(CardRepository cardRepository, CardMapper cardMapper) {
        this.cardRepository = cardRepository;
        this.cardMapper = cardMapper;
    }

    /**
     * Save a card.
     *
     * @param cardDTO the entity to save
     * @return the persisted entity
     */
    public CardDTO save(CardDTO cardDTO) {
        log.debug("Request to save Card : {}", cardDTO);
        Card card = cardMapper.toEntity(cardDTO);
        card = cardRepository.save(card);
        return cardMapper.toDto(card);
    }

    /**
     * Get all the cards.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<CardDTO> findAll() {
        log.debug("Request to get all Cards");
        return cardRepository.findAll().stream()
            .map(cardMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one card by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public CardDTO findOne(Long id) {
        log.debug("Request to get Card : {}", id);
        Card card = cardRepository.findOne(id);
        return cardMapper.toDto(card);
    }

    /**
     * Delete the card by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Card : {}", id);
        cardRepository.delete(id);
    }

    /**
     * Get all the cards.
     *
     * @return the list of entities by field id
     */
    @Transactional(readOnly = true)
    public List<CardDTO> findByField_Id(Long fieldId) {
        log.debug("Request to get all Cards by Field Id: {}", fieldId);
        return cardRepository.findByField_Id(fieldId).stream()
            .map(cardMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

}
