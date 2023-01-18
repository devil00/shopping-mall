package com.alethio.orderservice.client;

import com.alethio.orderservice.model.dto.ItemDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


@FeignClient(name = "itemWarehouseFeignClient", url = "${item.warehouse.url}")
public interface ItemWarehouseClient {

    @PutMapping("/v1/items/{itemType}")
    ItemDTO updateItem(@PathVariable String itemType, @PathVariable ItemDTO item);

    @GetMapping("/v1/items/{itemType}/{itemId}")
    ItemDTO getItem(@PathVariable String itemType, @PathVariable Integer itemId);

    @PatchMapping("/v1/items/{itemType}/{itemId}/{quantity}")
    ItemDTO updateItemStock(@PathVariable String itemType, @PathVariable Integer itemId, @PathVariable Long quantity);
}
