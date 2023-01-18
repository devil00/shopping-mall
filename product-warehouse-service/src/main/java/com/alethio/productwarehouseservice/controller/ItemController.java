package com.alethio.productwarehouseservice.controller;

import com.alethio.productwarehouseservice.exception.ResourceNotFoundException;
import com.alethio.productwarehouseservice.model.Item;
import com.alethio.productwarehouseservice.model.ItemType;
import com.alethio.productwarehouseservice.model.dto.ItemDTO;
import com.alethio.productwarehouseservice.service.CopyBeanService;
import com.alethio.productwarehouseservice.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/v1/items")
@Slf4j
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private CopyBeanService copyBeanService;

    @PutMapping("/{itemType}/{itemId}")
    public ResponseEntity<?> updateItem(@PathVariable String itemType, @PathVariable int itemId, @RequestBody ItemDTO item) {
        ItemType itemTypeEnum = ItemType.findItemTypeByName(itemType);
        if(Objects.isNull(itemTypeEnum)) {
            return ResponseEntity.badRequest().body(String.format("Not a valid item-type %s", itemType));
        }
        try {
            itemService.updateItem(itemTypeEnum, item, itemId);
            return ResponseEntity.ok().body(String.format("Item with id- %s and type - %s : %s", itemId, itemType, item));
        } catch (ResourceNotFoundException re) {
            log.error("Error getting item with id {}", itemId);
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            log.error("Error updating item with id {}", itemId, ex);
            return ResponseEntity.internalServerError().body(String.format("Error updating item with id %s", item.getId()));
        }
    }

    @GetMapping("/{itemType}/{itemId}")
    public ResponseEntity<?> getItem(@PathVariable String itemType, @PathVariable int itemId) {
        ItemType itemTypeEnum = ItemType.findItemTypeByName(itemType);
        if(Objects.isNull(itemTypeEnum)) {
            return ResponseEntity.badRequest().body(String.format("Not a valid item-type %s", itemType));
        }
        try {
            Item item = itemService.findItem(itemTypeEnum, itemId);
            if(Objects.isNull(item)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Not found item with id %s", itemId));
            }
            return ResponseEntity.ok(copyBeanService.mapToItemDTO(itemTypeEnum, item));
        } catch (ResourceNotFoundException ene) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ene.getMessage());
        } catch (Exception ex) {
            log.error("Error getting item with id {}", itemId);
            return ResponseEntity.internalServerError().body(String.format("Error getting item with id %s", itemId));
        }
    }

    @PostMapping("/{itemType}")
    public ResponseEntity<?> createItem(@PathVariable String itemType, @RequestBody ItemDTO itemDTO) {
        ItemType itemTypeEnum = ItemType.findItemTypeByName(itemType);
        if(Objects.isNull(itemTypeEnum)) {
            return ResponseEntity.badRequest().body(String.format("Not a valid item-type %s", itemType));
        }
        try {
            Item itemDb = itemService.createItem(itemTypeEnum, itemDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(copyBeanService.mapToItemDTO(itemTypeEnum, itemDb));
        } catch (Exception ex) {
            log.error("Creation failed" , ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }

    }

    @PatchMapping("{itemType}/{itemId}/{quantity}")
    public ResponseEntity<?> updateItemStock(@PathVariable String itemType, @PathVariable int itemId, @PathVariable Long quantity) {
        ItemType itemTypeEnum = ItemType.findItemTypeByName(itemType);
        if(Objects.isNull(itemTypeEnum)) {
            return ResponseEntity.badRequest().body(String.format("Not a valid item-type %s", itemType));
        }
        try {
            return ResponseEntity.ok(copyBeanService.mapToItemDTO(itemTypeEnum, itemService.updateStockQuantity(itemTypeEnum, itemId, quantity)));
        } catch (ResourceNotFoundException ene) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ene.getMessage());
        } catch (Exception ex) {
            log.error("Error getting item with id {}", itemId);
            return ResponseEntity.internalServerError().body(String.format("Error getting item with id %s", itemId));
        }
    }

}
