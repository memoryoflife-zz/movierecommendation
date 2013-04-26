/*     */ import emarket.client.AbstractClientAgent;
/*     */ import emarket.client.IllegalCancelBidException;
/*     */ import emarket.client.IllegalClientBidException;
/*     */ import emarket.client.interfaces.IAsset;
/*     */ import emarket.client.interfaces.IBid;
/*     */ import emarket.client.interfaces.IMarketHistoryBid;
/*     */ import emarket.client.interfaces.IMarketRepresentation;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class agent16 extends AbstractClientAgent
/*     */ {
/*     */   private IMarketRepresentation[] mr;
/*     */   private IAsset[] assets;
/*  27 */   private boolean IHaveMyTravelTickets = false;
/*     */ 
/*  30 */   private boolean IHaveMyRoomsBooked = false;
/*     */ 
/*  34 */   private int _flightAgentState = 0;
/*     */   public static final int flightPhase1 = 0;
/*     */   public static final int flightPhase2 = 1;
/*     */   public static final int flightPhase3 = 2;
/*  42 */   private final int _phase2StartTime = 34; private final int _phase3StartTime = 66;
/*     */ 
/*  45 */   private int _roomAgentState = 1;
/*     */   public static final int roomAggressive = 0;
/*     */   public static final int roomPassive = 1;
/*     */   public static final boolean DEBUG = false;
/*     */ 
/*     */   public void runAgentTasks()
/*     */   {
/*  64 */     IMarketRepresentation localIMarketRepresentation = null;
/*     */ 
/*  67 */     this.mr = getNoticeBoardDetails();
/*     */ 
/*  69 */     this.assets = getAssets();
/*     */ 
/*  73 */     String str1 = "flight to melbourne";
/*     */ 
/*  82 */     String str2 = "room";
/*     */ 
			//Buying flight tickets
				
/*  93 */     IMarketHistoryBid[] arrayOfIMarketHistoryBid = getMarketHistoryBidsForMarket(getMarket(str1).getMarketName(), getCurrentTime() - 1L);
/*  94 */     if (arrayOfIMarketHistoryBid.length != 0)
/*     */     {
/* 103 */       for (int i = 0; i < arrayOfIMarketHistoryBid.length; i++);
/*     */     }
/*     */ 
/* 111 */     localIMarketRepresentation = getMarket(str1);
/*     */ 
/* 119 */     for (int i = 0; i < this.assets.length; i++) {
/* 120 */       if (this.assets[i].getGoodName().equals(str1))
/*     */       {
/* 122 */         if (this.assets[i].getQuantity() != this.assets[i].getDesiredMinimumQuantity()) break;
/* 123 */         this.IHaveMyTravelTickets = true; break;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 138 */     if ((localIMarketRepresentation.isMarketOpen()) && (!this.IHaveMyTravelTickets)) {
/* 139 */       processFlightMarket(str1);
/*     */     }
/*     */ 

				//Buying voucher for hotel 
/* 151 */     for (int i = 0; i < this.assets.length; i++)
/* 152 */       if ((this.assets[i].getGoodName().equals(str2)) && (this.assets[i].getQuantity() >= this.assets[i].getDesiredMinimumQuantity()))
/*     */       {
/* 154 */         this.IHaveMyRoomsBooked = true;
/*     */       }
/*     */     
/* 159 */     if (!this.IHaveMyRoomsBooked)
/*     */     {
/* 164 */       IMarketRepresentation[] localObject1 = getBedMarkets();
/*     */ 
/* 170 */       int j = 0;
/*     */ 
/* 176 */       for (int k = 0; k < localObject1.length; k++)
/*     */       {
/* 179 */         IMarketHistoryBid[] localObject2 = getMarketHistoryBidsForMarket(localObject1[k].getMarketName(), getCurrentTime() - 1L);
/* 180 */         if (localObject2.length != 0)
/*     */         {
/* 189 */           for (int n = 0; n < localObject2.length; n++);
/*     */         }
/*     */ 
/* 194 */         localIMarketRepresentation = localObject1[k];
/* 195 */         if ((localIMarketRepresentation.isMarketOpen()) && (getCurrentTime() == localIMarketRepresentation.getOpeningTime()))
/*     */         {
/* 199 */           IBid[] arrayOfIBid2 = getAllSellBidsForMarket(localIMarketRepresentation.getMarketName());
/*     */ 
/* 202 */           int i2 = 0;
/* 203 */           for (int i3 = 0; i3 < arrayOfIBid2.length; i3++) {
/* 204 */             if (arrayOfIBid2[i3].getGoodName().equals(str2)) {
/* 205 */               i2++;
/*     */             }
/*     */           }
/*     */ 
/* 209 */           j += i2;  //get total number of vouchers on sale
/*     */ 
/* 211 */           int i3 = 0;
/* 212 */           for (int i4 = 0; i4 < this.assets.length; i4++) {
/* 213 */             if (this.assets[i4].getGoodName().equals(str2)) {
/* 214 */               i3 = this.assets[i4].getDesiredMinimumQuantity() - this.assets[i4].getQuantity();
/*     */             }
/*     */           }
/*     */ 
/*     */           try
/*     */           {
/* 220 */             if (this._roomAgentState == 0)
/*     */             {
/* 225 */               if ((i2 > 1) && (i2 > i3))
/*     */               {
/* 227 */                 submitBid(localIMarketRepresentation.getMarketName(), str2, 70L, i2 - 1);
/* 228 */               } else if (i2 > 0) {
/* 229 */                 submitBid(localIMarketRepresentation.getMarketName(), str2, 5L, i3);
/*     */               }
/* 231 */               submitBid(localIMarketRepresentation.getMarketName(), str2, 35L, 1);
/*     */             }
/* 236 */             else if (j > i3) {
/* 237 */               submitBid(localIMarketRepresentation.getMarketName(), str2, 60L, i3);
/*     */             }
/*     */           }
/*     */           catch (IllegalClientBidException localIllegalClientBidException3)
/*     */           {
/* 242 */             localIllegalClientBidException3.printStackTrace();
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 256 */     //Buying entertainment ticket
/* 262 */     for (int j = 0; j < this.assets.length; j++)
/*     */     {
/* 267 */       if ((this.assets[j].getGoodName().startsWith("ticket")) && (this.assets[j].getDesiredMinimumQuantity() > 0) && (this.assets[j].getQuantity() < this.assets[j].getDesiredMinimumQuantity()))
/*     */       {
/* 272 */         String localObject1 = this.assets[j].getGoodName();
/*     */ 
/* 274 */         localIMarketRepresentation = getMarket(localObject1);
/*     */ 
/* 277 */         IBid[] arrayOfIBid1 = getAllSellBidsForMarket(localIMarketRepresentation.getMarketName());
/*     */ 
/* 279 */         if ((arrayOfIBid1 != null) && (arrayOfIBid1.length > 0))
/*     */         {
/* 288 */           IBid localObject2 = null;
/* 289 */           for (int i1 = 0; i1 < arrayOfIBid1.length; i1++) {
/* 290 */             IBid localIBid = arrayOfIBid1[i1];
/* 291 */             if (localIBid.getGoodName().equals(localObject1)) {
/* 292 */               if (localObject2 == null)
/* 293 */                 localObject2 = localIBid;
/* 294 */               else if ((localObject2).getAmount() < localIBid.getAmount()) {
/* 295 */                 localObject2 = localIBid;
/*     */               }
/*     */ 
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/* 307 */           if (localObject2 != null) {
/*     */             try
/*     */             {
/* 310 */               if ((((IBid)localObject2).getAmount() > -100L) && (((IBid)localObject2).getAmount() < 100L)) {
/* 311 */                 submitBid(localIMarketRepresentation.getMarketName(), localObject1, (localObject2).getAmount(), this.assets[j].getDesiredMinimumQuantity());
/*     */               }
/*     */             }
/*     */             catch (IllegalClientBidException localIllegalClientBidException2)
/*     */             {
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
				//Selling entertainment ticket 
/* 323 */     for (int j = 0; j < this.assets.length; j++)
/* 324 */       if ((this.assets[j].getGoodName().startsWith("ticket")) && (this.assets[j].getDesiredMinimumQuantity() == 0) && (this.assets[j].getQuantity() > 0))
/*     */       {
/* 327 */         String localObject1 = this.assets[j].getGoodName();
/* 328 */         localIMarketRepresentation = getMarket(localObject1);
/*     */ 
/* 331 */         IBid[]arrayOfIBid1 = getOutstandingSellBids(localIMarketRepresentation.getMarketName(), localObject1);
/*     */ 
/* 335 */         if ((arrayOfIBid1 == null) && (localIMarketRepresentation.isMarketOpen()))
/*     */         {
/*     */           try
/*     */           {
/* 358 */             int m = this.assets[j].getQuantity() - this.assets[j].getDesiredMinimumQuantity();
/*     */ 
/* 360 */             if (m > 1)
/*     */             {
/* 363 */               submitBid(localIMarketRepresentation.getMarketName(), localObject1, -2147483648L, 1);
/*     */ 
/* 366 */               submitBid(localIMarketRepresentation.getMarketName(), localObject1, -60L, m - 1);
/* 367 */             } else if (m == 1)
/*     */             {
/* 370 */               submitBid(localIMarketRepresentation.getMarketName(), localObject1, -60L, 1);
/*     */ 
/* 374 */               submitBid(localIMarketRepresentation.getMarketName(), localObject1, -2147483648L, 1);
/*     */             }
/*     */           }
/*     */           catch (IllegalClientBidException localIllegalClientBidException1)
/*     */           {
/*     */           }
/*     */         }
/*     */       }
/*     */   }
/*     */ 
/*     */   private IMarketRepresentation[] getBedMarkets()
/*     */   {
/* 393 */     ArrayList localArrayList = new ArrayList(10);
/*     */ 
/* 395 */     String str = "room";
/*     */ 
/* 398 */     for (int i = 0; i < this.mr.length; i++) {
/* 399 */       String[] arrayOfString = this.mr[i].getGoodsAllowedByName();
/* 400 */       for (int j = 0; j < arrayOfString.length; j++) {
/* 401 */         if (arrayOfString[j].equals(str)) {
/* 402 */           localArrayList.add(this.mr[i]);
/*     */         }
/*     */       }
/*     */     }
/* 406 */     return (IMarketRepresentation[])localArrayList.toArray(new IMarketRepresentation[0]);
/*     */   }
/*     */ 
/*     */   private IMarketRepresentation getMarket(String paramString)
/*     */   {
/* 415 */     IMarketRepresentation localIMarketRepresentation = null;
/*     */ 
/* 418 */     for (int i = 0; i < this.mr.length; i++) {
/* 419 */       String[] arrayOfString = this.mr[i].getGoodsAllowedByName();
/* 420 */       for (int j = 0; j < arrayOfString.length; j++) {
/* 421 */         if (arrayOfString[j].equals(paramString)) {
/* 422 */           localIMarketRepresentation = this.mr[i];
/* 423 */           break;
/*     */         }
/*     */       }
/* 426 */       if (localIMarketRepresentation != null)
/*     */         break;
/*     */     }
/* 429 */     return localIMarketRepresentation;
/*     */   }
/*     */ 
/*     */   private String getCurrentAgentName()
/*     */   {
/* 438 */     return "[" + getClass().getName() + "]";
/*     */   }
/*     */ 
/*     */   private String parseAgentName(String paramString)
/*     */   {
/* 444 */     return paramString.substring(paramString.indexOf('[', 0) + 1, paramString.indexOf(',', paramString.indexOf(91, 0)));
/*     */   }
/*     */ 
/*     */   private void updateState(int paramInt)
/*     */   {
/* 453 */     if (paramInt > 34) {
/* 454 */       this._flightAgentState = 1;
/*     */     }
/* 456 */     if (paramInt > 66)
/* 457 */       this._flightAgentState = 2;
/*     */   }
/*     */ 
/*     */   public void processFlightMarket(String paramString)
/*     */   {
/* 463 */     updateState((int)getCurrentTime());
/* 464 */     processMarketActivity(paramString);
/* 465 */     computeValuation(paramString);
/*     */   }
/*     */ 
/*     */   private void processMarketActivity(String paramString)
/*     */   {
/* 471 */     IMarketRepresentation localIMarketRepresentation = getMarket(paramString);
/*     */ 
/* 479 */     for (int i = 0; i < this.assets.length; i++)
/* 480 */       if (this.assets[i].getGoodName().equals(paramString))
/*     */       {
/* 482 */         if (this.assets[i].getQuantity() != this.assets[i].getDesiredMinimumQuantity()) break;
/* 483 */         this.IHaveMyTravelTickets = true; break;
/*     */       }
/*     */   }
/*     */ 
/*     */   private void computeValuation(String paramString)
/*     */   {
/* 499 */     IMarketRepresentation localIMarketRepresentation = getMarket(paramString);
/*     */ 
/* 502 */     if ((localIMarketRepresentation.isMarketOpen()) && (!this.IHaveMyTravelTickets))
/*     */     {
/*     */       IBid[] arrayOfIBid;
/* 504 */       if (getOutstandingBuyBids(localIMarketRepresentation.getMarketName()) == null) {
/* 505 */         arrayOfIBid = getAllSellBidsForMarket(localIMarketRepresentation.getMarketName());
/*     */         try
/*     */         {
/* 511 */           submitBid(localIMarketRepresentation.getMarketName(), paramString, 250L, 2);
/* 512 */           submitBid(localIMarketRepresentation.getMarketName(), paramString, 300L, 2);
/*     */         }
/*     */         catch (IllegalClientBidException localIllegalClientBidException1) {
/* 515 */           localIllegalClientBidException1.printStackTrace();
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 523 */         arrayOfIBid = getOutstandingBuyBids(localIMarketRepresentation.getMarketName());
/* 524 */         long l = arrayOfIBid[0].getAmount() + 50L;
/*     */ 
/* 527 */         if (localIMarketRepresentation.getLastClearingPrice() < 250L)
/* 528 */           l = 250L;
/* 529 */         else if (localIMarketRepresentation.getLastClearingPrice() < 350L)
/* 530 */           l = 350L;
/* 531 */         else if (localIMarketRepresentation.getLastClearingPrice() < 400L)
/* 532 */           l = 400L;
/*     */         else {
/* 534 */           l = localIMarketRepresentation.getLastClearingPrice() - 10L;
/*     */         }
/*     */ 
/* 537 */         if (this._flightAgentState == 0) {
/* 538 */           if (l > 350L)
/* 539 */             l = 350L;
/* 540 */         } else if (this._flightAgentState == 1) {
/* 541 */           if (l > 450L)
/* 542 */             l = 450L;
/* 543 */         } else if (this._flightAgentState == 2) {
/* 544 */           l = localIMarketRepresentation.getLastClearingPrice() + 10L;
/*     */         }
/*     */ 
/* 553 */         for (int i = 0; i < arrayOfIBid.length; i++)
/*     */         {
/* 558 */           if (localIMarketRepresentation.getAction().equals("Submit and Cancel")) {
/*     */             try {
/* 560 */               cancelBid(arrayOfIBid[i]);
/*     */             }
/*     */             catch (IllegalCancelBidException localIllegalCancelBidException)
/*     */             {
/* 564 */               localIllegalCancelBidException.printStackTrace();
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */         try
/*     */         {
/* 574 */           submitBid(localIMarketRepresentation.getMarketName(), paramString, l, 4);
/*     */         }
/*     */         catch (IllegalClientBidException localIllegalClientBidException2)
/*     */         {
/* 581 */           localIllegalClientBidException2.printStackTrace();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\Profiles\fituser\Desktop\samples\
 * Qualified Name:     agent16
 * JD-Core Version:    0.6.2
 */