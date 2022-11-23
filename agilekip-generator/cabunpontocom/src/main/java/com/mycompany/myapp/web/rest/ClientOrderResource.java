package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.ClientOrderRepository;
import com.mycompany.myapp.service.ClientOrderService;
import com.mycompany.myapp.service.dto.ClientOrderDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.ClientOrder}.
 */
@RestController
@RequestMapping("/api")
public class ClientOrderResource {

    private final Logger log = LoggerFactory.getLogger(ClientOrderResource.class);

    private final ClientOrderService clientOrderService;

    private final ClientOrderRepository clientOrderRepository;

    public ClientOrderResource(ClientOrderService clientOrderService, ClientOrderRepository clientOrderRepository) {
        this.clientOrderService = clientOrderService;
        this.clientOrderRepository = clientOrderRepository;
    }

    /**
     * {@code GET  /client-orders} : get all the clientOrders.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clientOrders in body.
     */
    @GetMapping("/client-orders")
    public List<ClientOrderDTO> getAllClientOrders() {
        log.debug("REST request to get all ClientOrders");
        return clientOrderService.findAll();
    }

    /**
     * {@code GET  /client-orders/:id} : get the "id" clientOrder.
     *
     * @param id the id of the clientOrderDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the clientOrderDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/client-orders/{id}")
    public ResponseEntity<ClientOrderDTO> getClientOrder(@PathVariable Long id) {
        log.debug("REST request to get ClientOrder : {}", id);
        Optional<ClientOrderDTO> clientOrderDTO = clientOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(clientOrderDTO);
    }
}
