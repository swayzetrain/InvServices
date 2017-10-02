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
	
	public List<Quantity> GetQuantitesByItemFiltering(Integer itemId, String itemName, Integer instanceid) {
		
		if (null == itemName && null == itemId) {
			
			ArrayList<Item> itemList = itemRepository.findByInstanceid(instanceid);
			List<Integer> itemIdList = new ArrayList<Integer>();
			
			for(int i = 0; i < itemList.size(); i++) {
				
				itemIdList.add(itemList.get(i).getItemid());
				
			}
			
			List<Quantity> quantityList = quantityRepository.findByItemidIn(itemIdList);
			
			return quantityList;
			
		}
		
		if (null != itemName && null != itemId) {
			
			List<Quantity> quantityList = new ArrayList<Quantity>();
			Item item = itemRepository.findByItemidAndInstanceid(itemId, instanceid);
			
			if (item.getItemname().equalsIgnoreCase(itemName)) {
				
				quantityList.add(quantityRepository.findByItemid(itemId));
				
			}
			
			return quantityList;
			
		}
		
		if (null != itemName) {
			
			List<Integer> itemIdList = new ArrayList<Integer>();
			
			List<Item> itemByName = itemRepository.findByItemnameAndInstanceid(itemName, instanceid);
			
			for (int i = 0; i < itemByName.size(); i++) {
				
				itemIdList.add(itemByName.get(i).getItemid());
				
			}
			
			return quantityRepository.findByItemidIn(itemIdList);
			
		}
		
		List<Quantity> quantityList = new ArrayList<Quantity>();
		
		quantityList.add(quantityRepository.findByItemid(itemId));
		
		return quantityList;
		
	}
	
	public boolean CheckItemInstance(Integer itemid, Integer instanceid) {
		
		Item item = itemRepository.findByItemidAndInstanceid(itemid, instanceid);
		
		if(null == item) {
			return false;
		}
		
		return true;
		
	}
	
}
