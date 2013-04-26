/*     */ import emarket.client.AbstractClientAgent;
/*     */ import emarket.client.IllegalCancelBidException;
/*     */ import emarket.client.IllegalClientBidException;
/*     */ import emarket.client.interfaces.IAsset;
/*     */ import emarket.client.interfaces.IBid;
/*     */ import emarket.client.interfaces.IMarketHistoryBid;
/*     */ import emarket.client.interfaces.IMarketRepresentation;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class agent13 extends AbstractClientAgent
/*     */ {
/*     */   private IMarketRepresentation[] mr;
/*     */   private IAsset[] assets;
/*  25 */   private boolean IHaveMyTravelTickets = false;
/*     */ 
/*  28 */   private boolean IHaveMyRoomsBooked = false;
/*     */ 
/*     */   public void runAgentTasks()
/*     */   {
/*  43 */     IMarketRepresentation localIMarketRepresentation = null;
/*     */ 
/*  46 */     this.mr = getNoticeBoardDetails();
/*  47 */     this.assets = getAssets();
/*     */ 
/*  51 */     String str1 = "flight to melbourne";
/*     */ 
/*  56 */     String str2 = "room";
/*     */ 
/*  64 */     IMarketHistoryBid[] arrayOfIMarketHistoryBid = getMarketHistoryBidsForMarket(getMarket(str1).getMarketName(), getCurrentTime() - 1L);
/*  65 */     if (arrayOfIMarketHistoryBid.length != 0)
/*     */     {
/*  71 */       for (int i = 0; i < arrayOfIMarketHistoryBid.length; i++);
/*     */     }
/*     */ 
/*  79 */     localIMarketRepresentation = getMarket(str1);
/*     */ 
/*  86 */     for (int i = 0; i < this.assets.length; i++) {
/*  87 */       if (this.assets[i].getGoodName().equals(str1))
/*     */       {
/*  89 */         if (this.assets[i].getQuantity() < this.assets[i].getDesiredMinimumQuantity()) break;
/*  90 */         this.IHaveMyTravelTickets = true; break;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 101 */     if ((localIMarketRepresentation.isMarketOpen()) && (!this.IHaveMyTravelTickets))
/*     */     {
/*     */       IBid[] arrayOfIBid1;
/* 103 */       if (getOutstandingBuyBids(localIMarketRepresentation.getMarketName()) == null) {
/* 104 */         arrayOfIBid1 = getAllSellBidsForMarket(localIMarketRepresentation.getMarketName());
/*     */         try
/*     */         {
/* 109 */           submitBid(localIMarketRepresentation.getMarketName(), str1, 400L, 8);
/*     */         }
/*     */         catch (IllegalClientBidException localIllegalClientBidException1) {
/* 112 */           localIllegalClientBidException1.printStackTrace();
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 118 */         arrayOfIBid1 = getOutstandingBuyBids(localIMarketRepresentation.getMarketName());
/*     */ 
/* 125 */         for (int k = 0; k < arrayOfIBid1.length; k++)
/*     */         {
/* 129 */           if (localIMarketRepresentation.getAction().equals("Submit and Cancel")) {
/*     */             try {
/* 131 */               cancelBid(arrayOfIBid1[k]);
/*     */             }
/*     */             catch (IllegalCancelBidException localIllegalCancelBidException) {
/* 134 */               localIllegalCancelBidException.printStackTrace();
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */         try
/*     */         {
/* 145 */           if (getCurrentTime() < 80L)
/*     */           {
/* 147 */             submitBid(localIMarketRepresentation.getMarketName(), str1, 400L, 8);
/*     */           }
/* 149 */           else if ((getCurrentTime() >= 80L) && (getCurrentTime() < 99L))
/*     */           {
/* 151 */             submitBid(localIMarketRepresentation.getMarketName(), str1, 500L, 8);
/*     */           }
/*     */           else
/*     */           {
/* 155 */             submitBid(localIMarketRepresentation.getMarketName(), str1, 600L, 8);
/*     */           }
/*     */ 
/*     */         }
/*     */         catch (IllegalClientBidException localIllegalClientBidException2)
/*     */         {
/* 161 */           localIllegalClientBidException2.printStackTrace();
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 171 */     for (int j = 0; j < this.assets.length; j++)
/* 172 */       if ((this.assets[j].getGoodName().equals(str2)) && (this.assets[j].getQuantity() >= this.assets[j].getDesiredMinimumQuantity()))
/*     */       {
/* 174 */         this.IHaveMyRoomsBooked = true;
/*     */       }
/*     */ 
/* 179 */     if (!this.IHaveMyRoomsBooked)
/*     */     {
/* 182 */       IMarketRepresentation[] localObject1 = getBedMarkets();
/*     */ 
/* 188 */      int m = 0;
/*     */ 
/* 190 */       for (int n = 0; n < localObject1.length; n++)
/*     */       {
/* 194 */         IMarketHistoryBid[] localObject2 = getMarketHistoryBidsForMarket(localObject1[n].getMarketName(), getCurrentTime() - 1L);
/* 195 */         if (localObject2.length != 0)
/*     */         {
/* 202 */           for (int i1 = 0; i1 < localObject2.length; i1++);
/*     */         }
/*     */ 
/* 207 */         localIMarketRepresentation = localObject1[n];
/* 208 */         if ((localIMarketRepresentation.isMarketOpen()) && (getCurrentTime() == localIMarketRepresentation.getOpeningTime()))
/*     */         {
/* 211 */           IBid[] arrayOfIBid3 = getAllSellBidsForMarket(localIMarketRepresentation.getMarketName());
/*     */ 
/* 214 */           int i3 = 0;
/* 215 */           for (int i4 = 0; i4 < arrayOfIBid3.length; i4++) {
/* 216 */             if (arrayOfIBid3[i4].getGoodName().equals(str2)) i3++;
/*     */ 
/*     */           }
/*     */ 
/* 220 */           m += i3;
/*     */           try
/*     */           {
/* 228 */             if (this.assets[n].getDesiredMinimumQuantity() - this.assets[n].getQuantity() < 0)
/*     */             {
/* 235 */               submitBid(localIMarketRepresentation.getMarketName(), str2, 150L, 8);
/*     */             }
/*     */           }
/*     */           catch (IllegalClientBidException localIllegalClientBidException5) {
/* 239 */             localIllegalClientBidException5.printStackTrace();
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 252 */     Object localObject1 = null;
/*     */     IBid[] arrayOfIBid2;
/* 257 */     for (int m = 0; m < this.assets.length; m++)
/*     */     {
/* 260 */       if ((this.assets[m].getGoodName().startsWith("ticket")) && (this.assets[m].getDesiredMinimumQuantity() > 0) && (this.assets[m].getQuantity() < this.assets[m].getDesiredMinimumQuantity()))
/*     */       {
/* 265 */         localObject1 = this.assets[m].getGoodName();
/*     */ 
/* 267 */         localIMarketRepresentation = getMarket((String)localObject1);
/*     */ 
/* 269 */         arrayOfIBid2 = getAllSellBidsForMarket(localIMarketRepresentation.getMarketName());
/*     */ 
/* 271 */         if ((arrayOfIBid2 != null) && (arrayOfIBid2.length > 0))
/*     */         {
/* 277 */           IBid localObject2 = null;
/* 278 */           for (int i2 = 0; i2 < arrayOfIBid2.length; i2++) {
/* 279 */             IBid localIBid = arrayOfIBid2[i2];
/* 280 */             if (localIBid.getGoodName().equals(localObject1)) {
/* 281 */               if (localObject2 == null) localObject2 = localIBid;
/* 282 */               else if (((IBid)localObject2).getAmount() > localIBid.getAmount()) {
/* 283 */                 localObject2 = localIBid;
/*     */               }
/*     */ 
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/* 291 */           if (localObject2 != null)
/*     */           {
/*     */             try
/*     */             {
/* 299 */               if (((IBid)localObject2).getAmount() <= 70L)
/* 300 */                 submitBid(localIMarketRepresentation.getMarketName(), (String)localObject1, ((IBid)localObject2).getAmount() + 2L, 24 - this.assets[m].getQuantity());
/* 301 */               else if ((((IBid)localObject2).getAmount() > 70L) && (((IBid)localObject2).getAmount() <= 95L))
/*     */               {
/* 303 */                 submitBid(localIMarketRepresentation.getMarketName(), (String)localObject1, ((IBid)localObject2).getAmount() + 1L, 24 - this.assets[m].getQuantity());
/*     */               }
/*     */ 
/*     */             }
/*     */             catch (IllegalClientBidException localIllegalClientBidException4)
/*     */             {
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 319 */     for (int m = 0; m < this.assets.length; m++)
/* 320 */       if ((this.assets[m].getGoodName().startsWith("ticket")) && (this.assets[m].getDesiredMinimumQuantity() == 0) && (this.assets[m].getQuantity() > 0)) {
/* 321 */         localObject1 = this.assets[m].getGoodName();
/* 322 */         localIMarketRepresentation = getMarket((String)localObject1);
/*     */ 
/* 324 */         arrayOfIBid2 = getOutstandingSellBids(localIMarketRepresentation.getMarketName(), (String)localObject1);
/*     */ 
/* 328 */         if ((arrayOfIBid2 == null) && (localIMarketRepresentation.isMarketOpen()))
/*     */         {
/*     */           try
/*     */           {
/* 334 */             submitBid(localIMarketRepresentation.getMarketName(), (String)localObject1, -96L, this.assets[m].getQuantity());
/*     */           }
/*     */           catch (IllegalClientBidException localIllegalClientBidException3)
/*     */           {
/*     */           }
/*     */         }
/*     */       }
/*     */   }
/*     */ 
/*     */   private IMarketRepresentation[] getBedMarkets()
/*     */   {
/* 351 */     ArrayList localArrayList = new ArrayList(10);
/*     */ 
/* 353 */     String str = "room";
/*     */ 
/* 356 */     for (int i = 0; i < this.mr.length; i++) {
/* 357 */       String[] arrayOfString = this.mr[i].getGoodsAllowedByName();
/* 358 */       for (int j = 0; j < arrayOfString.length; j++) {
/* 359 */         if (arrayOfString[j].equals(str)) {
/* 360 */           localArrayList.add(this.mr[i]);
/*     */         }
/*     */       }
/*     */     }
/* 364 */     return (IMarketRepresentation[])localArrayList.toArray(new IMarketRepresentation[0]);
/*     */   }
/*     */ 
/*     */   private IMarketRepresentation getMarket(String paramString)
/*     */   {
/* 372 */     IMarketRepresentation localIMarketRepresentation = null;
/*     */ 
/* 375 */     for (int i = 0; i < this.mr.length; i++) {
/* 376 */       String[] arrayOfString = this.mr[i].getGoodsAllowedByName();
/* 377 */       for (int j = 0; j < arrayOfString.length; j++) {
/* 378 */         if (arrayOfString[j].equals(paramString)) {
/* 379 */           localIMarketRepresentation = this.mr[i];
/* 380 */           break;
/*     */         }
/*     */       }
/* 383 */       if (localIMarketRepresentation != null) break;
/*     */     }
/* 385 */     return localIMarketRepresentation;
/*     */   }
/*     */ }

/* Location:           D:\Profiles\fituser\Desktop\samples\
 * Qualified Name:     agent13
 * JD-Core Version:    0.6.2
 */