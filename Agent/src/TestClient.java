import emarket.client.AbstractClientAgent;
import emarket.client.interfaces.IMarketRepresentation;
import emarket.client.interfaces.IMarketHistoryBid;
import emarket.client.IllegalClientBidException;
import emarket.client.IllegalCancelBidException;
import emarket.client.interfaces.IBid;
import emarket.client.interfaces.IAsset;

import java.util.ArrayList;

/**
 * This is a test client agent for the emarket environment
 */

public class TestClient extends AbstractClientAgent{
  // Global variable holding the current market representations. Each market
  // representation is an the actual front to the market itself. Thus,
  // each IMarketRepresentation object is effectively a different market
  private IMarketRepresentation[] mr;

  // Global variable holding the current assets of this agent
  private IAsset[] assets;

  // Indicates whether we have already bought our travel tickets
  private boolean IHaveMyTravelTickets = false;

  // Indicates whether we have already booked enough rooms for the trip
  private boolean IHaveMyRoomsBooked = false;

  /**
   * This is the method run by the emarket environment to simulate market
   * activity on the agent's behalf. All actions which you wish to undertake
   * within the emarket environment (such as buying goods, etc) should be
   * written within this method.
   */
  public void runAgentTasks() {
    // Just a test to see what the current time is within the emarket
    // environment
//    System.out.println("CURRENT TIME: "+getCurrentTime());

    // Local variable holding the market representation for each market
    // we will be looking at.
    IMarketRepresentation m = null;

    // Sets the global variables for this round
    mr = getNoticeBoardDetails();
    assets = getAssets();

    // This is the name of the good we are looking for in our search for
    // flight tickets. Note: this is not the market name
    String flightsGoodName = "flight to melbourne";

    // This is the name of the good we are looking for in our search for
    // booking rooms/beds for each night that our clients require. Note:
    // this is not the market name
    String roomGoodName = "room";

    // ----------------------------------------------------- market history bid
    // If we want to check what happened in the previous timestep for the markets,
    // we can do this by calling getMarketHistoryBidForMarket(<String market>, <long time>);
    // This will return us an array of IMarketHistoryBid[], and each object
    // represents a single bid which was cleared (ie, either bought or sold)
    // within the market.
    IMarketHistoryBid[] lastTimestepFlights = getMarketHistoryBidsForMarket(getMarket(flightsGoodName).getMarketName(), getCurrentTime()-1);
    if(lastTimestepFlights.length==0) {
      // If IMarketHistoryBid.length==0, this means none of our agents bids
      // were cleared in this market.
//      System.out.println("No activity for flights market in the previous timestep.");
    } else {
      // If IMarketHistoryBid.length>0, then something happened last round...
      for (int i = 0; i < lastTimestepFlights.length; i++) {
//        System.out.println(lastTimestepFlights[i]);
      }
    }

    // --------------------------------------------------------travel agency
    // We get the available market which provides sales of this good.
    // (getMarket(String) is a private method at the end of this class)
    m = getMarket(flightsGoodName);

    // We look at each of our assets to find our flight ticket assets.
    // Each Asset which we need to acquire has a desired quantity greater than
    // zero. In other words, IAsset.getDesiredMinimumQuantity() > 0.
    // Thus, we can check whether the quantity of the asset we've acquired
    // has reached the desired minimum quanity.
    for (int i = 0; i < assets.length; i++) {
      if( assets[i].getGoodName().equals(flightsGoodName)) {
//        System.out.println(assets[i].getQuantity());
        if (assets[i].getQuantity()==assets[i].getDesiredMinimumQuantity()) {
          IHaveMyTravelTickets = true;
        }
        break;
      }
    }

    // Next, if we already havent got our tickets, then we go ahead and attempt
    // to submit a bid for them.

    // Before we can do anything with the market, we must first
    // check whether it is open!
    if(m.isMarketOpen() && !IHaveMyTravelTickets) {
      // Check to see whether we have outstanding bids yet.
      if(getOutstandingBuyBids(m.getMarketName())==null) {
        IBid[] sellbids = getAllSellBidsForMarket(m.getMarketName());
//        System.out.println(sellbids[0].getAmount());
        // If there are not outstanding bids, place 4 bids for 4 flight tickets
        // at 300 dollars each.
        try {
          submitBid(m.getMarketName(), flightsGoodName, 550, 8);
        } catch (IllegalClientBidException ice) {
          // Something went wrong with the submission of the bid.
          ice.printStackTrace();
        }
      } else {
        // If there are outstanding bids, then obviously they didnt sell
        // on the market in the last round. Here, our strategy is simple:
        // if they didn't sell, bump up the buying price by 50 dollars.
        IBid [] buybids = getOutstandingBuyBids(m.getMarketName());
        long amount = buybids[0].getAmount()+50;
        // We must re-submit new bids for this strategy to work. We cannot
        // directly change the old price. So first, we go through and cancel
        // any outstanding buy bids we have for this market. Then we
        // submit new bids at the new price.
        for (int i = 0; i < buybids.length; i++) {

          // Just want to make sure that we are allowed to cancel a buy
          // bid in this market...
          if (m.getAction().equals(m.ACTION_SUBMIT_AND_CANCEL)) {
            try {
              cancelBid(buybids[i]);
            } catch (IllegalCancelBidException ice) {
              // Something went wrong with cancelling this bid.
              ice.printStackTrace();
            }
          }
        }
        // Submit 4 new bids for 4 flight tickets at the new amount, "amount"
        try {
          submitBid(m.getMarketName(), flightsGoodName, amount, 4);
        } catch(IllegalClientBidException ice) {
          // Something went wrong with the submission. We can check by calling
          // the ice.getReason() method and checking it against the IBidException
          // public static int REASON_ list.
          ice.printStackTrace();
        }
      }
    }

    // ----------------------------------------------------------- find rooms

    // Again, we look for the booking rooms asset to see whether our
    // desired quantity has been acheived. If it has, then we dont need
    // to purchase any more tickets
    for (int i = 0; i < assets.length; i++) {
      if (assets[i].getGoodName().equals(roomGoodName) && assets[i].getQuantity()>=assets[i].getDesiredMinimumQuantity()) {
        IHaveMyRoomsBooked = true;
      }
    }

    // We only need to do something if our rooms/beds aren't purchased
    if(!IHaveMyRoomsBooked) {
      // Get the most recently available markets which sell rooms/beds
      // (getBedMarkets() is a private method found at the end of this class)
      IMarketRepresentation [] beds = getBedMarkets();

      // Our strategy to purchase this good is pretty simple: for every
      // market available, keep submitting bids until we've purchased
      // enough beds. Note: we cannot cancel bids in these markets in
      // accordance with the assignment specification
      int totalBedsBiddedForThisRound = 0;

      for (int i = 0; i < beds.length; i++) {

        // Our TestClient wants to check the history of how the beds market
        // went....
        IMarketHistoryBid [] bedMarketHistory = getMarketHistoryBidsForMarket(beds[i].getMarketName(), getCurrentTime()-1);
        if(bedMarketHistory.length==0) {
          // If IMarketHistoryBid.length==0, this means none of our agents bids
          // were cleared in this market.
//          System.out.println("No activity for beds/rooms market in the previous timestep.");
        } else {
          // If IMarketHistoryBid.length>0, then something happened last round...
          // Do a printout of what happened with our TestClient's bids
          for (int a = 0; a < bedMarketHistory.length; a++) {
//            System.out.println(bedMarketHistory[a]);
          }
        }

        m = beds[i];
        if(m.isMarketOpen() && getCurrentTime() == m.getOpeningTime()) {
          // We first want to see how many sell bids are on offer here so
          // we can keep track of how many bids we make.
          IBid[] sellbids = getAllSellBidsForMarket(m.getMarketName());

          // Counts the available beds for this market on sale...
          int numberOfSellBeds = 0;
          for (int a = 0; a < sellbids.length; a++) {
            if(sellbids[a].getGoodName().equals(roomGoodName)) numberOfSellBeds++;
          }

          // Sums up the total number of bed bids made in this round.
          totalBedsBiddedForThisRound += numberOfSellBeds;

          // places bid at the very start for $0 and hopes for the best
          try {
            submitBid(m.getMarketName(), roomGoodName, 10, numberOfSellBeds);
          } catch (IllegalClientBidException ice) {
            // Something went wrong with the submission of the bid
            ice.printStackTrace();
          }
          break;
        }
      }
    }

    // ------------------------------------------------ entertainment tickets
    // Purchasing entertainment tickets becomes a little more complicated
    // since we have to sell some of our tickets on the open exchange market
    // environment as well as purchase other tickets at the same time.

    // Temporary good name for the entertainment tickets
    String goodName = null;

    // All goods which are entertainment tickets all have names which
    // begin with the string "ticket". We check whether our asset is an
    // entertainment ticket with the line: assets[i].getGoodName().startsWith("ticket").
    for (int i = 0; i < assets.length; i++) {
      // Start off by placing buy bids for assets which we need to acquire...
      // We need to acquire all ticket assets with a desired quantity > 0
      if(assets[i].getGoodName().startsWith("ticket") &&
         assets[i].getDesiredMinimumQuantity()>0 &&
         assets[i].getQuantity()<assets[i].getDesiredMinimumQuantity()) {

        //  Name of good we need.
        goodName = assets[i].getGoodName();
        // Market representation of market which sells the good.
        m = getMarket(goodName);
        // Check whether there are any existing bids on offer for this good.
        IBid [] sellbids = getAllSellBidsForMarket(m.getMarketName());

        if(sellbids!=null && sellbids.length>0) {
          // We have sell bids to view! :)
          // We now loop through each sell bid, looking for the good we wish
          // to bid for. Since it is an 'exchange', then we peruse all the
          // available sell bids to see which sell bid is the cheapest. This
          // is our simple strategy.
          IBid cheapestBid = null;
          for (int a = 0; a < sellbids.length; a++) {
            IBid bid = sellbids[a];
            if (bid.getGoodName().equals(goodName)) {
              if(cheapestBid==null) cheapestBid = bid;
              else if (cheapestBid.getAmount() > bid.getAmount())
                cheapestBid = bid;
            }
          }
          // If cheapestBid is not null, then this the cheapest bid on the
          // market. We are taking a gamble, however - there could be many
          // agents with the same strategy which aim at getting the cheapest
          // price which we have to compete with! If we are unlucky, our
          // bid will be unsuccessful.
          if(cheapestBid!=null) {
            try {
              submitBid(m.getMarketName(), goodName, 100L, assets[i].getDesiredMinimumQuantity());
            } catch (IllegalClientBidException ic) {
              System.out.println("TestClient1: submit failed: reason="+ic.getReason());
            }
          }
        }
      }
    }

    // Now, we attempt get rid of our entertainment tickets that we do not
    // need (ie, assets with desired quantity of zero).
    for (int i = 0; i < assets.length; i++) {
      if (assets[i].getGoodName().startsWith("ticket") && assets[i].getDesiredMinimumQuantity()==0 && assets[i].getQuantity()>0) {
        goodName = assets[i].getGoodName();
        m = getMarket(goodName);
        // Places sell bids for the tickets which we obviously dont need anymore..
        IBid [] outstandingsellbids = getOutstandingSellBids(m.getMarketName(), goodName);

        // If we haven't already made a sell bid for this good, make one.
        // This is our simple selling strategy.
        if(outstandingsellbids==null && m.isMarketOpen()) {
          try {
            // Submitting a sell bid is made the same way as a buy bid, except
            // for the negative amount. Here, the selling price is 25 dollars,
            // though we pass in the parameter of -25.
            submitBid(m.getMarketName(), goodName, -25, assets[i].getQuantity());
          } catch (IllegalClientBidException ix) {
            // For some reason the submission of the sell bid has failed
            System.out.println("TestClient1: submit2 failed: reason="+ix.getReason());
          }
        }
      }
    }
  }

  /**
   * Convinience method which retrieves all the markets which sell rooms/beds
   * for the night.
   */
  private IMarketRepresentation[] getBedMarkets() {
    ArrayList m = new ArrayList(10);
    // Name of the good we are looking for
    String goodName = "room";
    // Finds the market for the goodName. To do this, we can search the
    // IMarketRepresentation objects for the markets by goods allowed
    for (int i = 0; i < mr.length; i++) {
      String [] goods = mr[i].getGoodsAllowedByName();
      for (int a = 0; a < goods.length; a++) {
        if (goods[a].equals(goodName)) {
          m.add(mr[i]);
        }
      }
    }
    return (IMarketRepresentation[])m.toArray(new IMarketRepresentation[]{});
  }

  /**
   * Convinience method which gets the first market available which
   * sells a particular good.
   */
  private IMarketRepresentation getMarket(String goodName) {
    IMarketRepresentation m = null;
    // Finds the market for the goodName. To do this, we can search the
    // IMarketRepresentation objects for the markets by goods allowed
    for (int i = 0; i < mr.length; i++) {
      String [] goods = mr[i].getGoodsAllowedByName();
      for (int a = 0; a < goods.length; a++) {
        if (goods[a].equals(goodName)) {
          m = mr[i];
          break;
        }
      }
      if (m!=null) break;
    }
    return m;
  }
}