package com.swayzetrain.inventory.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swayzetrain.inventory.common.model.Item;
import com.swayzetrain.inventory.common.model.Quantity;
import com.swayzetrain.inventory.common.repository.ItemRepository;
import com.swayzetrain.inventory.common.repository.QuantityRepository;

@Service
public class QuantityRequestService {
	
	@Autowired
	private QuantityRepository quantityRepository;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private ItemRepository itemRepository;

	public boolean QuantityExists(Integer quantityId) {
		
		Quantity quantity = quantityRepository.findByQuantityid(quantityId);
		
		if (null == quantity) {
			
			return false;
			
		}
		
		return true;
		
	}
	
	public void PopulateUpdatedQuantity(Quantity newQuantity, Integer quantityId) {
		
		newQuantity.setQuantityid(quantityId);
		
		Quantity oldQuantity = quantityRepository.findByQuantityid(quantityId);
		
		if (null == newQuantity.getItemid()) {
			
			newQuantity.setItemid(oldQuantity.getItemid());
			
		}
		
		if (null == newQuantity.getQuantity()) {
			
			newQuantity.setQuantity(oldQuantity.getQuantity());
			
		}
		
		if (null == newQuantity.getQuantitytype()) {
			
			newQuantity.setQuantitytype(oldQuantity.getQuantitytype());
			
		}
		
		if (null == newQuantity.getDatemodified()) {
			
			newQuantity.setDatemodified(commonService.setTimestamp());
			
		}
		
	}
	
	public List<Quantity> GetQuantitesByItemFiltering(Integer itemId, String itemName) {
		
		if (null == itemName && null == itemId) {
			
			return quantityRepository.findAll();
			
		}
		
		if (null != itemName && null != itemId) {
			
			List<Quantity> quantityList = new ArrayList<Quantity>();
			Item item = itemRepository.findByItemid(itemId);
			
			if (item.getItemname().equalsIgnoreCase(itemName)) {
				
				quantityList.add(quantityRepository.findByItemid(itemId));
				
			}
			
			return quantityList;
			
		}
		
		if (null != itemName) {
			
			List<Integer> itemIdList = new ArrayList<Integer>();
			
			List<Item> itemByName = itemRepository.findByItemname(itemName);
			
			for (int i = 0; i < itemByName.size(); i++) {
				
				itemIdList.add(itemByName.get(i).getItemid());
				
			}
			
			return quantityRepository.findByItemidIn(itemIdList);
			
		}
		
		List<Quantity> quantityList = new ArrayList<Quantity>();
		
		quantityList.add(quantityRepository.findByItemid(itemId));
		
		return quantityList;
		
	}
	
}
