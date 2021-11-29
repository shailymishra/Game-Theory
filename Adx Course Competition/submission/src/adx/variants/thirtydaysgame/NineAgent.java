package adx.variants.thirtydaysgame;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import adx.exceptions.AdXException;
import adx.structures.Campaign;
import adx.structures.MarketSegment;
import adx.structures.SimpleBidEntry;
import adx.util.Logging;
import adx.utils.MarketSegmentProbabilities;

/**
 * An example of a simple agent playing the TwoDays game.
 * 
 * @author Enrique Areyan Viqueira
 */
public class NineAgent extends ThirtyDaysThirtyCampaignsAgent {

	private Map<String,Double>  pacingmultiplier = null;
	private Set<String> prevBidEntries= null;
	MarketSegmentProbabilities objMarketSegmentProbabilities = new MarketSegmentProbabilities();
	private Double DECREASING_FACTOR;
	private Double INCREASING_FACTOR;
	private static Double INITVALUE = 1.1;
  public NineAgent(String host, int port) {
    super(host, port);
    prevBidEntries =  new HashSet<String>();
	this.pacingmultiplier = objMarketSegmentProbabilities.initializePacingMultiplier(INITVALUE);
	DECREASING_FACTOR = 1.01;
	INCREASING_FACTOR = 1.1;
  }

  @Override
  protected ThirtyDaysBidBundle getBidBundle(int day) {
    try {
      Campaign c = null;
      if (day <= 30) {
        Logging.log("[-] Bid for campaign " + day + " which is: " + this.setCampaigns[day - 1]);
        c = this.setCampaigns[day - 1];
      } 
      else {
        throw new AdXException("[x] Bidding for invalid day " + day + ", bids in this game are only for day 1 or 2.");
      }
      // Bidding only on the exact market segment of the campaign.
      Set<SimpleBidEntry> bidEntries = new HashSet<SimpleBidEntry>();
      Double temBudget;
      String msString;
      Logging.log("[o] bidEntries- segment = " + c.getMarketSegment().name());
      double sum= 0.0, prob,count;
      if(c.getBudget()<1) {    	  
    	  if(prevBidEntries!=null) {
    		  for(String be:prevBidEntries) {
    	    	  msString = objMarketSegmentProbabilities.getAppropriateProbKey(be);
    	    	  this.pacingmultiplier.put(msString,this.pacingmultiplier.get(msString)*INCREASING_FACTOR);  
    		  }
    		  prevBidEntries.clear();
    	  }
    	  prevBidEntries.add(c.getMarketSegment().name());
    	  bidEntries.add(new SimpleBidEntry(c.getMarketSegment(), (c.getBudget() / (double) c.getReach())*this.pacingmultiplier.get(objMarketSegmentProbabilities.getAppropriateProbKey(c.getMarketSegment().name())), c.getBudget()));
      }
      else {
//    	  pacingmultiplier = pacingmultiplier/1.01;
//    	  if(pacingmultiplier<1.0) {
//    		  pacingmultiplier = 1.0;
//    	  }
    	  if(prevBidEntries!=null) {
    		  for(String be:prevBidEntries) {
    	    	  msString = objMarketSegmentProbabilities.getAppropriateProbKey(be);
    	    	  this.pacingmultiplier.put(msString,this.pacingmultiplier.get(msString)/DECREASING_FACTOR);  
    		  }
    		  prevBidEntries.clear();
    	  }
    	  prevBidEntries.add(c.getMarketSegment().name());
    	  bidEntries.add(new SimpleBidEntry(c.getMarketSegment(), (c.getBudget() / (double) c.getReach())*this.pacingmultiplier.get(objMarketSegmentProbabilities.getAppropriateProbKey(c.getMarketSegment().name())), c.getBudget()));

      }
      Logging.log("[-] bidEntries = " + bidEntries);
      return new ThirtyDaysBidBundle(day, c.getId(), c.getBudget(), bidEntries);
//      return new ThirtyDaysBidBundle(day, c.getId(), 4.256, bidEntries);
    } catch (AdXException e) {
      Logging.log("[x] Something went wrong getting the bid bundle: " + e.getMessage());
    }
    return null;
  }

  /**
   * Agent's main method.
   * 
   * @param args
   */
  public static void main(String[] args) {
	  NineAgent agent = new NineAgent("localhost", 9898);
    agent.connect("agent0");
  }

}
