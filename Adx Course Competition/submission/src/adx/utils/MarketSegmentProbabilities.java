package adx.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import adx.structures.MarketSegment;

public class MarketSegmentProbabilities {
	private HashMap<String,Double> probMap = new HashMap<String,Double>();
	private HashMap<String,String> segOpMap = new HashMap<String,String>();

	public MarketSegmentProbabilities() {
		super();
		segOpMap.put("M", "F");
		segOpMap.put("F", "M");
		segOpMap.put("Y", "O");
		segOpMap.put("O", "Y");
		segOpMap.put("H", "L");
		segOpMap.put("L", "H");
		segOpMap.put("M", "F");
		segOpMap.put("F", "M");
		segOpMap.put("Y", "O");
		segOpMap.put("O", "Y");
		segOpMap.put("H", "L");
		segOpMap.put("L", "H");
		probMap.put("M_Y_L",0.1836);
		probMap.put("M_Y_H",0.0517);
		probMap.put("M_O_L",0.1795);
		probMap.put("M_O_H",0.0808);
		probMap.put("F_Y_L",0.198);
		probMap.put("F_Y_H",0.0256);
		probMap.put("F_O_L",0.2401);
		probMap.put("F_O_H",0.0407);
		probMap.put("M",0.4956);
		probMap.put("F",0.5044);
		probMap.put("M_Y",0.2353);
		probMap.put("M_O",0.2603);
		probMap.put("F_Y",0.2236);
		probMap.put("F_O",0.2808);
		probMap.put("M_L",0.3631);
		probMap.put("M_H",0.1325);
		probMap.put("F_L",0.4381);
		probMap.put("F_H",0.0663);
		probMap.put("Y_L",0.3816);
		probMap.put("Y_H",0.0773);
		probMap.put("O_L",0.4196);
		probMap.put("O_H",0.1215);
		probMap.put("L",0.8012);
		probMap.put("H",0.1988);
		probMap.put("Y",0.4589);
		probMap.put("O",0.5411);
	}
	public Double getAppropriateProb(String marketSeg) {
		String returnKey = getAppropriateProbKey(marketSeg);
		if(probMap.containsKey(returnKey))
			return probMap.get(returnKey);
		else {
			System.out.println("[x] key "+returnKey+" not found: "+marketSeg);
			return 1.0;
		}
	}
	public Double getAppropriateProbSet(String returnKey) {
		if(probMap.containsKey(returnKey))
			return probMap.get(returnKey);
		else {
			System.out.println("[x] key "+returnKey+" not found: ");
			return 1.0;
		}
	}
	public Double calculateBidValue(String marketSeg) {
		String returnKey = getAppropriateProbKey(marketSeg);

//	    System.out.println("[o] "+marketSeg+" "+returnKey);
		if(probMap.containsKey(returnKey)) {
			HashSet<String> dependantProbs = findAllDependantProb(returnKey);
			double sum = 0;
			for(String depPrb : dependantProbs) {
				if(probMap.containsKey(depPrb))
					sum += (probMap.get(depPrb));
			}
			return probMap.get(returnKey)/sum;
		}			
		else {
			System.out.println("[x] key "+returnKey+" not found: "+marketSeg);
			return 1.0;
		}
	}
	public HashSet<String> findAllDependantProb(String returnKey) {
		// TODO Auto-generated method stub
		String[] keysStr = returnKey.split("_");
		HashSet<String> returnSet = new HashSet<String>();
		Set<String> allSet = probMap.keySet();
		for(String keySet : allSet) {
			for( int i=0;i<keysStr.length;i++) {
				if(keySet.contains(keysStr[i])) {
					returnSet.add(keySet);
				}
			}
		}
		return returnSet;
	}
	public String getAppropriateProbKey(String marketSeg) {
		String returnKey = "";
		if(marketSeg.startsWith("MALE")) {
			returnKey = "M";
		}
		else if(marketSeg.startsWith("FEMALE")) {
			returnKey = "F";
		}
		if(marketSeg.contains("YOUNG")) {
			if(returnKey.equalsIgnoreCase(""))
				returnKey = "Y";
			else
				returnKey += "_Y";
		}
		else if(marketSeg.contains("OLD")) {
			if(returnKey.equalsIgnoreCase(""))
				returnKey = "O";
			else
				returnKey += "_O";
		}

		if(marketSeg.contains("HIGH")) {
			if(returnKey.equalsIgnoreCase(""))
				returnKey = "H";
			else
				returnKey += "_H";
		}
		else if(marketSeg.contains("LOW")) {
			if(returnKey.equalsIgnoreCase(""))
				returnKey = "L";
			else
				returnKey += "_L";
		}
		return returnKey;
	}
	public double calculateBidValueSegment(MarketSegment marketSegment) {
		// TODO Auto-generated method stub
		MarketSegment[] subsegments = marketSegment.values();
		return 0;
	}
	public HashSet<MarketSegment> findAllSubsets(MarketSegment parent) {
		// TODO Auto-generated method stub
		HashSet<MarketSegment> returnSeg = new HashSet<MarketSegment>();
		MarketSegment[] subsegments = parent.values();
		String name = parent.name();
		boolean selectSeg = true;
		String[] subNames = name.replace("_INCOME", "").split("_");
		for(MarketSegment subseg:subsegments) {
			selectSeg = true;
			for(int i=0;i<subNames.length;i++) {
				if(!subseg.name().contains(subNames[i])) {
					selectSeg = false;
				}
				else if(subseg.name().contains("FEMALE") && subNames[i].equalsIgnoreCase("MALE")) {
					selectSeg = false;
				}
				
			}
			if(selectSeg) {
				returnSeg.add(subseg);
			}
		}
		return returnSeg;
	}
	public Map<String, Double> initializePacingMultiplier(Double initVal) {
		// TODO Auto-generated method stub
		Map<String, Double> returnObj = new HashMap<String, Double>();
		returnObj.put("M_Y_L",initVal);
		returnObj.put("M_Y_H",initVal);
		returnObj.put("M_O_L",initVal);
		returnObj.put("M_O_H",initVal);
		returnObj.put("F_Y_L",initVal);
		returnObj.put("F_Y_H",initVal);
		returnObj.put("F_O_L",initVal);
		returnObj.put("F_O_H",initVal);
		returnObj.put("M",initVal);
		returnObj.put("F",initVal);
		returnObj.put("M_Y",initVal);
		returnObj.put("M_O",initVal);
		returnObj.put("F_Y",initVal);
		returnObj.put("F_O",initVal);
		returnObj.put("M_L",initVal);
		returnObj.put("M_H",initVal);
		returnObj.put("F_L",initVal);
		returnObj.put("F_H",initVal);
		returnObj.put("Y_L",initVal);
		returnObj.put("Y_H",initVal);
		returnObj.put("O_L",initVal);
		returnObj.put("O_H",initVal);
		returnObj.put("L",initVal);
		returnObj.put("H",initVal);
		returnObj.put("Y",initVal);
		returnObj.put("O",initVal);
		return returnObj;
	}
	
}
