/*     */ import emarket.client.AbstractClientAgent;
/*     */ import emarket.client.IllegalCancelBidException;
/*     */ import emarket.client.IllegalClientBidException;
/*     */ import emarket.client.interfaces.IAsset;
/*     */ import emarket.client.interfaces.IBid;
/*     */ import emarket.client.interfaces.IMarketHistoryBid;
/*     */ import emarket.client.interfaces.IMarketRepresentation;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class agent15 extends AbstractClientAgent
/*     */ {
/*     */   private IMarketRepresentation[] mr;
/*     */   private IAsset[] assets;
/*  25 */   private boolean IHaveMyTravelTickets = false;
/*     */ 
/*  28 */   private boolean IHaveMyRoomsBooked = false;
/*     */ 
/*  31 */   private long oldBid = 0L;
/*  32 */   private boolean biddedOnRooms = false;
/*     */ 
/*     */   public void runAgentTasks()
/*     */   {
/*  49 */     IMarketRepresentation localIMarketRepresentation = null;
/*     */ 
/*  52 */     this.mr = getNoticeBoardDetails();
/*  53 */     this.assets = getAssets();
/*     */ 
/*  57 */     String str1 = "flight to melbourne";
/*     */ 
/*  62 */     String str2 = "room";
/*     */ 
/*  70 */     IMarketHistoryBid[] arrayOfIMarketHistoryBid = getMarketHistoryBidsForMarket(getMarket(str1).getMarketName(), getCurrentTime() - 1L);
/*  71 */     if (arrayOfIMarketHistoryBid.length != 0)
/*     */     {
/*  77 */       for (int i = 0; i < arrayOfIMarketHistoryBid.length; i++);
/*     */     }
/*     */ 
/*  85 */     localIMarketRepresentation = getMarket(str1);
/*     */ 
/*  92 */     for (int i = 0; i < this.assets.length; i++) {
/*  93 */       if (this.assets[i].getGoodName().equals(str1))
/*     */       {
/*  95 */         if (this.assets[i].getQuantity() != this.assets[i].getDesiredMinimumQuantity()) break;
/*  96 */         this.IHaveMyTravelTickets = true; break;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 107 */     if ((localIMarketRepresentation.isMarketOpen()) && (!this.IHaveMyTravelTickets))
/*     */     {
/*     */       IBid[] arrayOfIBid1;
/* 110 */       if (getOutstandingBuyBids(localIMarketRepresentation.getMarketName()) == null) {
/* 111 */         arrayOfIBid1 = getAllSellBidsForMarket(localIMarketRepresentation.getMarketName());
/*     */         try
/*     */         {
/* 115 */           submitBid(localIMarketRepresentation.getMarketName(), str1, flightBidAmount(), ticketsRequired(str1));
/* 116 */           this.oldBid = flightBidAmount();
/*     */         }
/*     */         catch (IllegalClientBidException localIllegalClientBidException1) {
/* 119 */           localIllegalClientBidException1.printStackTrace();
/*     */         }
/*     */ 
/*     */       }
/* 123 */       else if (bidChanged()) {
/* 124 */         arrayOfIBid1 = getOutstandingBuyBids(localIMarketRepresentation.getMarketName());
/* 125 */         long l1 = flightBidAmount();
/*     */ 
/* 130 */         for (int n = 0; n < arrayOfIBid1.length; n++)
/*     */         {
/* 133 */           if (localIMarketRepresentation.getAction().equals("Submit and Cancel")) {
/*     */             try {
/* 135 */               cancelBid(arrayOfIBid1[n]);
/*     */             }
/*     */             catch (IllegalCancelBidException localIllegalCancelBidException) {
/* 138 */               localIllegalCancelBidException.printStackTrace();
/*     */             }
/*     */           }
/*     */         }
/*     */ 
/*     */         try
/*     */         {
/* 145 */           submitBid(localIMarketRepresentation.getMarketName(), str1, l1, 1);
/* 146 */           this.oldBid = flightBidAmount();
/*     */         }
/*     */         catch (IllegalClientBidException localIllegalClientBidException2)
/*     */         {
/* 151 */           localIllegalClientBidException2.printStackTrace();
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 162 */     for (int j = 0; j < this.assets.length; j++)
/* 163 */       if ((this.assets[j].getGoodName().equals(str2)) && (this.assets[j].getQuantity() >= this.assets[j].getDesiredMinimumQuantity()))
/* 164 */         this.IHaveMyRoomsBooked = true;
/*     */    
/*     */    
/* 169 */     if (!this.IHaveMyRoomsBooked)
/*     */     {
/* 172 */       IMarketRepresentation[] localObject1 = getBedMarkets();
/*     */ 
/* 174 */       int k = 0;
/*     */ 
/* 176 */       for (int m = 0; m < localObject1.length; m++)
/*     */       {
/* 179 */          IMarketHistoryBid[] localObject2 = getMarketHistoryBidsForMarket(localObject1[m].getMarketName(), getCurrentTime() - 1L);
/* 180 */         if (localObject2.length != 0)
/*     */         {
/* 187 */           for (int i1 = 0; i1 < localObject2.length; i1++);
/*     */         }
/*     */ 
/* 192 */         localIMarketRepresentation = localObject1[m];
/* 193 */         if ((localIMarketRepresentation.isMarketOpen()) && (getCurrentTime() == localIMarketRepresentation.getOpeningTime()))
/*     */         {
/* 196 */           IBid[] localObject3 = getAllSellBidsForMarket(localIMarketRepresentation.getMarketName());
/*     */ 
/* 201 */           if (this.biddedOnRooms) break;
/*     */           try {
/* 203 */             submitBid(localIMarketRepresentation.getMarketName(), str2, 150L, roomsRequired(str2));
/* 204 */             this.biddedOnRooms = true;
/*     */           }
/*     */           catch (IllegalClientBidException localIllegalClientBidException3) {
/* 207 */             localIllegalClientBidException3.printStackTrace();
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 221 */     Object localObject1 = null;
/*     */     IBid[] arrayOfIBid2;
/* 226 */     for (int k = 0; k < this.assets.length; k++)
/*     */     {
/* 229 */       if ((this.assets[k].getGoodName().startsWith("ticket")) && (this.assets[k].getDesiredMinimumQuantity() > 0) && (this.assets[k].getQuantity() < this.assets[k].getDesiredMinimumQuantity()))
/*     */       {
/* 234 */         localObject1 = this.assets[k].getGoodName();
/*     */ 
/* 236 */         localIMarketRepresentation = getMarket((String)localObject1);
/*     */ 
/* 238 */         arrayOfIBid2 = getAllSellBidsForMarket(localIMarketRepresentation.getMarketName());
/* 239 */         IBid[] localObject2 = getAllBuyBidsForMarket(localIMarketRepresentation.getMarketName());
/* 240 */        IBid localObject3 = null;
/* 241 */         long l2 = 57L;
/*     */ 
/* 244 */         if ((localObject2 != null) && (localObject2.length > 0)) {
/* 245 */           for (int i4 = 0; i4 < localObject2.length; i4++) {
/* 246 */             IBid localObject4 = localObject2[i4];
/* 247 */             if (localObject4.getGoodName().equals(localObject1)) {
/* 248 */               if (localObject3 == null) localObject3 = localObject4;
/* 249 */               else if (((IBid)localObject3).getAmount() > localObject4.getAmount()) {
/* 250 */                 localObject3 = localObject4;
/*     */               }
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 258 */         if ((localObject3 != null) && (((IBid)localObject3).getAmount() < 57L)) {
/* 259 */           l2 = ((IBid)localObject3).getAmount() + 3L;
/* 260 */           if (getCurrentTime() >= 70L) {
/* 261 */             l2 += l2 + getCurrentTime() - 70L;
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 266 */         if ((arrayOfIBid2 != null) && (arrayOfIBid2.length > 0)) {
/*     */           try {
/* 268 */             submitBid(localIMarketRepresentation.getMarketName(), (String)localObject1, l2, this.assets[k].getDesiredMinimumQuantity());
/*     */           } catch (IllegalClientBidException localIllegalClientBidException5) {
/* 270 */             System.out.println("TestClient1: submit failed: reason=" + localIllegalClientBidException5.getReason());
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 278 */     for (int k = 0; k < this.assets.length; k++)
/* 279 */       if ((this.assets[k].getGoodName().startsWith("ticket")) && (this.assets[k].getDesiredMinimumQuantity() == 0) && (this.assets[k].getQuantity() > 0)) {
/* 280 */         localObject1 = this.assets[k].getGoodName();
/* 281 */         localIMarketRepresentation = getMarket((String)localObject1);
/*     */ 
/* 283 */         arrayOfIBid2 = getOutstandingSellBids(localIMarketRepresentation.getMarketName(), (String)localObject1);
/* 284 */         IBid[] localObject2 = getOutstandingSellBids(localIMarketRepresentation.getMarketName(), (String)localObject1);
/* 285 */         int i2 = 0;
/*     */ 
/* 288 */         if ((localObject2 != null) && (localObject2.length > 0)) {
/* 289 */           for (int i3 = 0; i3 < localObject2.length; i3++) {
/* 290 */             i2++;
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 296 */         if ((arrayOfIBid2 == null) && (localIMarketRepresentation.isMarketOpen()))
/*     */         {
/*     */           try
/*     */           {
/* 301 */             if (i2 == 1) {
/* 302 */               submitBid(localIMarketRepresentation.getMarketName(), (String)localObject1, -99L, this.assets[k].getQuantity());
/*     */             }
/*     */             else
/* 305 */               submitBid(localIMarketRepresentation.getMarketName(), (String)localObject1, -65L, this.assets[k].getQuantity());
/*     */           }
/*     */           catch (IllegalClientBidException localIllegalClientBidException4)
/*     */           {
/* 309 */             System.out.println("TestClient1: submit2 failed: reason=" + localIllegalClientBidException4.getReason());
/*     */           }
/*     */         }
/*     */       }
/*     */   }
/*     */ 
/*     */   private IMarketRepresentation[] getBedMarkets()
/*     */   {
/* 321 */     ArrayList localArrayList = new ArrayList(10);
/*     */ 
/* 323 */     String str = "room";
/*     */ 
/* 326 */     for (int i = 0; i < this.mr.length; i++) {
/* 327 */       String[] arrayOfString = this.mr[i].getGoodsAllowedByName();
/* 328 */       for (int j = 0; j < arrayOfString.length; j++) {
/* 329 */         if (arrayOfString[j].equals(str)) {
/* 330 */           localArrayList.add(this.mr[i]);
/*     */         }
/*     */       }
/*     */     }
/* 334 */     return (IMarketRepresentation[])localArrayList.toArray(new IMarketRepresentation[0]);
/*     */   }
/*     */ 
/*     */   private IMarketRepresentation getMarket(String paramString)
/*     */   {
/* 342 */     IMarketRepresentation localIMarketRepresentation = null;
/*     */ 
/* 345 */     for (int i = 0; i < this.mr.length; i++) {
/* 346 */       String[] arrayOfString = this.mr[i].getGoodsAllowedByName();
/* 347 */       for (int j = 0; j < arrayOfString.length; j++) {
/* 348 */         if (arrayOfString[j].equals(paramString)) {
/* 349 */           localIMarketRepresentation = this.mr[i];
/* 350 */           break;
/*     */         }
/*     */       }
/* 353 */       if (localIMarketRepresentation != null) break;
/*     */     }
/* 355 */     return localIMarketRepresentation;
/*     */   }
/*     */ 
/*     */   private long flightBidAmount()
/*     */   {
/* 364 */     long l1 = getCurrentTime();
/* 365 */     long l2 = 0L;
/* 366 */     if (l1 <= 50L) {
/* 367 */       l2 = 300L;
/*     */     }
/* 369 */     else if ((l1 > 50L) && (l1 <= 60L)) {
/* 370 */       l2 = 400L;
/*     */     }
/* 372 */     else if ((l1 > 60L) && (l1 <= 70L)) {
/* 373 */       l2 = 500L;
/*     */     }
/* 375 */     else if (l1 > 70L)
/* 376 */       l1 = 550L;
/* 377 */     return l2;
/*     */   }
/*     */ 
/*     */   private int ticketsRequired(String paramString) {
/* 381 */     int i = 0;
/* 382 */     for (int j = 0; j < this.assets.length; j++) {
/* 383 */       if (this.assets[j].getGoodName().equals(paramString)) {
/* 384 */         i = 8 - this.assets[j].getQuantity();
/*     */       }
/*     */     }
/* 387 */     return i;
/*     */   }
/*     */ 
/*     */   private boolean bidChanged()
/*     */   {
/*     */     boolean bool;
/* 393 */     if (flightBidAmount() != this.oldBid)
/* 394 */       bool = true;
/*     */     else
/* 396 */       bool = false;
/* 397 */     return bool;
/*     */   }
/*     */ 
/*     */   private int roomsRequired(String paramString)
/*     */   {
/* 402 */     int i = 0;
/* 403 */     for (int j = 0; j < this.assets.length; j++) {
/* 404 */       if (this.assets[j].getGoodName().equals(paramString)) {
/* 405 */         i = this.assets[j].getDesiredMinimumQuantity() - this.assets[j].getQuantity();
/*     */       }
/*     */     }
/* 408 */     return i;
/*     */   }
/*     */ 
/*     */   private int roomsHolding(String paramString)
/*     */   {
/* 413 */     int i = 0;
/* 414 */     for (int j = 0; j < this.assets.length; j++) {
/* 415 */       if (this.assets[j].getGoodName().equals(paramString)) {
/* 416 */         i = this.assets[j].getQuantity();
/*     */       }
/*     */     }
/* 419 */     return i;
/*     */   }
/*     */ }

/* Location:           D:\Profiles\fituser\Desktop\samples\
 * Qualified Name:     agent15
 * JD-Core Version:    0.6.2
 */