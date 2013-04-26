/*     */ import emarket.client.AbstractClientAgent;
/*     */ import emarket.client.IllegalCancelBidException;
/*     */ import emarket.client.IllegalClientBidException;
/*     */ import emarket.client.interfaces.IAsset;
/*     */ import emarket.client.interfaces.IBid;
/*     */ import emarket.client.interfaces.IMarketHistoryBid;
/*     */ import emarket.client.interfaces.IMarketRepresentation;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class agent14 extends AbstractClientAgent
/*     */ {
/*     */   private IMarketRepresentation[] mr;
/*     */   private IAsset[] assets;
/*  24 */   private boolean IHaveMyTravelTickets = false;
/*     */ 
/*  27 */   private boolean IHaveMyRoomsBooked = false;
/*     */ 
/*  29 */   private boolean SubmitBid = false;
/*     */ 
/*  31 */   private long CurrentHighestPrice = 0L;
/*     */   private long PriceDifferent;
/*     */   boolean goodWasBought;
/*     */   int quality;
/*     */   String HasOtherBuyers;
/*  36 */   long lastprice = 0L;
/*     */ 
/*     */   public void runAgentTasks()
/*     */   {
/*  53 */     IMarketRepresentation localIMarketRepresentation = null;
/*     */ 
/*  56 */     this.mr = getNoticeBoardDetails();
/*  57 */     this.assets = getAssets();
/*     */ 
/*  61 */     String str1 = "flight to melbourne";
/*     */ 
/*  66 */     String str2 = "room";
/*     */ 
/*  74 */     IMarketHistoryBid[] arrayOfIMarketHistoryBid1 = getMarketHistoryBidsForMarket(getMarket(str1).getMarketName(), getCurrentTime() - 1L);
/*  75 */     if (arrayOfIMarketHistoryBid1.length != 0)
/*     */     {
/*  81 */       for (int i = 0; i < arrayOfIMarketHistoryBid1.length; i++);
/*     */     }
/*     */ 
/*  88 */     localIMarketRepresentation = getMarket(str1);
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
/* 104 */     if ((localIMarketRepresentation.isMarketOpen()) && (!this.IHaveMyTravelTickets))
/*     */     {
/*     */       IBid[] arrayOfIBid1;
/*     */       long l1;
/* 106 */       if (getOutstandingBuyBids(localIMarketRepresentation.getMarketName()) == null) {
/* 107 */         arrayOfIBid1 = getAllSellBidsForMarket(localIMarketRepresentation.getMarketName());
/* 108 */         l1 = arrayOfIBid1[0].getAmount();
/*     */ 
/* 110 */         if (l1 >= this.CurrentHighestPrice)
/*     */         {
/* 112 */           this.CurrentHighestPrice = l1;
/*     */         }
/*     */         try
/*     */         {
/* 116 */           submitBid(localIMarketRepresentation.getMarketName(), str1, 300L, 8);
/*     */         } catch (IllegalClientBidException localIllegalClientBidException1) {
/* 118 */           localIllegalClientBidException1.printStackTrace();
/*     */         }
/*     */       } else {
/* 121 */         arrayOfIBid1 = getAllSellBidsForMarket(localIMarketRepresentation.getMarketName());
/* 122 */         l1 = arrayOfIBid1[0].getAmount();
/*     */ 
/* 125 */         if (l1 >= this.CurrentHighestPrice)
/*     */         {
/* 127 */           this.CurrentHighestPrice = l1;
/* 128 */           this.PriceDifferent = (this.CurrentHighestPrice - l1);
/*     */         }
/*     */         else
/*     */         {
/*     */           IBid[] arrayOfIBid2;
/*     */           long l2;
/* 135 */           if ((getCurrentTime() > 40L) && (l1 <= 300L))
/*     */           {
/* 137 */             arrayOfIBid2 = getOutstandingBuyBids(localIMarketRepresentation.getMarketName());
/* 138 */             l2 = 300L;
/*     */ 
/* 140 */             for (int i1 = 0; i1 < arrayOfIBid2.length; i1++)
/*     */             {
/* 142 */               if (localIMarketRepresentation.getAction().equals("Submit and Cancel"))
/*     */                 try {
/* 144 */                   cancelBid(arrayOfIBid2[i1]);
/*     */                 } catch (IllegalCancelBidException localIllegalCancelBidException1) {
/* 146 */                   localIllegalCancelBidException1.printStackTrace();
/*     */                 }
/*     */             }
/*     */             try
/*     */             {
/* 151 */               submitBid(localIMarketRepresentation.getMarketName(), str1, l2, 8);
/* 152 */               this.goodWasBought = true;
/*     */             } catch (IllegalClientBidException localIllegalClientBidException2) {
/* 154 */               localIllegalClientBidException2.printStackTrace();
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/* 160 */           if ((getCurrentTime() == 99L) && (!this.goodWasBought))
/*     */           {
/* 162 */             arrayOfIBid2 = getOutstandingBuyBids(localIMarketRepresentation.getMarketName());
/* 163 */             l2 = 600L;
/*     */ 
/* 165 */             for (int i2 = 0; i2 < arrayOfIBid2.length; i2++)
/*     */             {
/* 167 */               if (localIMarketRepresentation.getAction().equals("Submit and Cancel"))
/*     */                 try {
/* 169 */                   cancelBid(arrayOfIBid2[i2]);
/*     */                 } catch (IllegalCancelBidException localIllegalCancelBidException2) {
/* 171 */                   localIllegalCancelBidException2.printStackTrace();
/*     */                 }
/*     */             }
/*     */             try
/*     */             {
/* 176 */               submitBid(localIMarketRepresentation.getMarketName(), str1, l2, 8);
/* 177 */               this.goodWasBought = true;
/*     */             } catch (IllegalClientBidException localIllegalClientBidException3) {
/* 179 */               localIllegalClientBidException3.printStackTrace();
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/* 186 */           this.PriceDifferent = (this.CurrentHighestPrice - l1);
/* 187 */           if (this.CurrentHighestPrice >= 550L)
/*     */           {
/* 189 */             if (this.PriceDifferent >= 160L)
/*     */             {
/* 191 */               arrayOfIBid2 = getOutstandingBuyBids(localIMarketRepresentation.getMarketName());
/* 192 */               l2 = l1 + 50L;
/*     */ 
/* 194 */               for (int i3 = 0; i3 < arrayOfIBid2.length; i3++)
/*     */               {
/* 196 */                 if (localIMarketRepresentation.getAction().equals("Submit and Cancel"))
/*     */                   try {
/* 198 */                     cancelBid(arrayOfIBid2[i3]);
/*     */                   } catch (IllegalCancelBidException localIllegalCancelBidException3) {
/* 200 */                     localIllegalCancelBidException3.printStackTrace();
/*     */                   }
/*     */               }
/*     */               try
/*     */               {
/* 205 */                 submitBid(localIMarketRepresentation.getMarketName(), str1, l2, 8);
/* 206 */                 this.goodWasBought = true;
/*     */               } catch (IllegalClientBidException localIllegalClientBidException4) {
/* 208 */                 localIllegalClientBidException4.printStackTrace();
/*     */               }
/*     */ 
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/* 215 */           if ((this.CurrentHighestPrice < 550L) && (this.CurrentHighestPrice >= 430L))
/*     */           {
/* 217 */             if (this.PriceDifferent >= 100L)
/*     */             {
/* 219 */               arrayOfIBid2 = getOutstandingBuyBids(localIMarketRepresentation.getMarketName());
/* 220 */               l2 = l1 + 50L;
/*     */ 
/* 222 */               for (int i4 = 0; i4 < arrayOfIBid2.length; i4++)
/*     */               {
/* 224 */                 if (localIMarketRepresentation.getAction().equals("Submit and Cancel"))
/*     */                   try {
/* 226 */                     cancelBid(arrayOfIBid2[i4]);
/*     */                   } catch (IllegalCancelBidException localIllegalCancelBidException4) {
/* 228 */                     localIllegalCancelBidException4.printStackTrace();
/*     */                   }
/*     */               }
/*     */               try
/*     */               {
/* 233 */                 submitBid(localIMarketRepresentation.getMarketName(), str1, l2, 8);
/* 234 */                 this.goodWasBought = true;
/*     */               } catch (IllegalClientBidException localIllegalClientBidException5) {
/* 236 */                 localIllegalClientBidException5.printStackTrace();
/*     */               }
/*     */ 
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/* 243 */           if (this.CurrentHighestPrice < 430L)
/*     */           {
/* 245 */             if (this.PriceDifferent >= 5L)
/*     */             {
/* 247 */               arrayOfIBid2 = getOutstandingBuyBids(localIMarketRepresentation.getMarketName());
/* 248 */               l2 = l1 + 50L;
/*     */ 
/* 250 */               for (int i5 = 0; i5 < arrayOfIBid2.length; i5++)
/*     */               {
/* 252 */                 if (localIMarketRepresentation.getAction().equals("Submit and Cancel"))
/*     */                   try {
/* 254 */                     cancelBid(arrayOfIBid2[i5]);
/*     */                   } catch (IllegalCancelBidException localIllegalCancelBidException5) {
/* 256 */                     localIllegalCancelBidException5.printStackTrace();
/*     */                   }
/*     */               }
/*     */               try
/*     */               {
/* 261 */                 submitBid(localIMarketRepresentation.getMarketName(), str1, l2, 8);
/* 262 */                 this.goodWasBought = true;
/*     */               } catch (IllegalClientBidException localIllegalClientBidException6) {
/* 264 */                 localIllegalClientBidException6.printStackTrace();
/*     */               }
/*     */ 
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
/* 277 */     int j = 0;
/* 278 */     for (int k = 0; k < this.assets.length; k++)
/* 279 */       if (this.assets[k].getGoodName().equals(str2))
/*     */       {
/* 281 */         this.quality = (this.assets[k].getDesiredMinimumQuantity() - this.assets[k].getQuantity());
/* 282 */         if (this.assets[k].getQuantity() >= this.assets[k].getDesiredMinimumQuantity())
/*     */         {
/* 284 */           this.IHaveMyRoomsBooked = true;
/*     */         }
/*     */       }
/*     */     
/* 289 */     if (!this.IHaveMyRoomsBooked)
/*     */     {
/* 292 */       IMarketRepresentation[] localObject1 = getBedMarkets();
/*     */ 
/* 294 */       for (int m = 0; m < localObject1.length; m++)
/*     */       {
/* 297 */         IMarketHistoryBid[] arrayOfIMarketHistoryBid2 = getMarketHistoryBidsForMarket(localObject1[m].getMarketName(), getCurrentTime() - 1L);
/* 298 */         if (arrayOfIMarketHistoryBid2.length != 0)
/*     */         {
/* 305 */           for (int i6 = 0; i6 < arrayOfIMarketHistoryBid2.length; i6++);
/*     */         }
/*     */ 
/* 310 */         localIMarketRepresentation = localObject1[m];
/*     */ 
/* 312 */         if ((localIMarketRepresentation.isMarketOpen()) && (getCurrentTime() == localIMarketRepresentation.getOpeningTime()))
/*     */         {
/* 315 */           IBid[] localObject2 = getAllSellBidsForMarket(localIMarketRepresentation.getMarketName());
/*     */ 
/* 317 */           int i7 = 0;
/* 318 */           for (int i8 = 0; i8 < localObject2.length; i8++) {
/* 319 */             if (localObject2[i8].getGoodName().equals(str2)) i7++;
/*     */ 
/*     */           }
/*     */ 
/*     */           try
/*     */           {
/* 327 */             if (i7 >= 200)
/*     */             {
/* 329 */               submitBid(localIMarketRepresentation.getMarketName(), str2, 10L, this.quality);
/*     */             }
/* 331 */             else if (i7 >= 120)
/*     */             {
/* 333 */               submitBid(localIMarketRepresentation.getMarketName(), str2, 60L, this.quality);
/*     */             }
/* 335 */             else if (i7 >= 60)
/*     */             {
/* 337 */               submitBid(localIMarketRepresentation.getMarketName(), str2, 70L, this.quality);
/*     */             }
/* 339 */             else if (i7 >= 30)
/*     */             {
/* 341 */               submitBid(localIMarketRepresentation.getMarketName(), str2, 80L, this.quality);
/*     */             }
/*     */             else
/*     */             {
/* 345 */               submitBid(localIMarketRepresentation.getMarketName(), str2, 100L, this.quality);
/*     */             }
/*     */           }
/*     */           catch (IllegalClientBidException localIllegalClientBidException7) {
/* 349 */             localIllegalClientBidException7.printStackTrace();
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 361 */     Object localObject1 = null;
/*     */ 
/* 367 */     int m = 0;
/*     */     IBid[] arrayOfIBid3;
/*     */   
/* 368 */     for (int n = 0; n < this.assets.length; n++)
/*     */     {
/* 370 */       if ((this.assets[n].getGoodName().startsWith("ticket")) && (this.assets[n].getDesiredMinimumQuantity() > 0) && (this.assets[n].getQuantity() < this.assets[n].getDesiredMinimumQuantity()))
/*     */       {
/* 374 */         ArrayList localObject2 = new ArrayList();
/* 375 */         long l4 = 0L;
/*     */ 
/* 377 */         m += this.assets[n].getDesiredMinimumQuantity();
/* 378 */         localObject1 = this.assets[n].getGoodName();
/*     */ 
/* 380 */         localIMarketRepresentation = getMarket((String)localObject1);
/*     */ 
/* 386 */         arrayOfIBid3 = getOutstandingBuyBids(localIMarketRepresentation.getMarketName(), (String)localObject1);
/* 387 */         if ((arrayOfIBid3 != null) && (arrayOfIBid3.length > 0))
/*     */         {
/* 388 */           for (int i9 = 0; i9 < arrayOfIBid3.length; i9++);
/*     */         }
/*     */ 
/* 393 */         IBid[] arrayOfIBid4 = getAllBuyBidsForMarket(localIMarketRepresentation.getMarketName());
/*     */         int i11;
/*     */         IBid localObject4;
/* 395 */         if ((arrayOfIBid4 != null) && (arrayOfIBid4.length > 0))
/*     */         {
/* 397 */           for (i11 = 0; i11 < arrayOfIBid4.length; i11++)
/*     */           {
/* 399 */             localObject4 = arrayOfIBid4[i11];
/* 400 */             if (((IBid)localObject4).getGoodName().equals(localObject1))
/*     */             {
/* 402 */               localObject2.add(Long.valueOf(((IBid)localObject4).getAmount()));
/*     */             }
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 408 */         if (((ArrayList)localObject2).size() == 0)
/*     */         {
/* 410 */           this.HasOtherBuyers = "no";
/*     */         }
/*     */         else
/*     */         {
/* 418 */           for (i11 = 0; i11 < ((ArrayList)localObject2).size(); i11++)
/*     */           {
/* 422 */             if (l4 < Long.valueOf(((ArrayList)localObject2).get(i11).toString()).longValue())
/*     */             {
/* 424 */               l4 = Long.valueOf(((ArrayList)localObject2).get(i11).toString()).longValue();
/*     */             }
/*     */           }
/*     */ 
/* 428 */           l4 += 1L;
/*     */           try
/*     */           {
/* 434 */             if (l4 < 50L)
/*     */             {
/* 436 */               submitBid(localIMarketRepresentation.getMarketName(), (String)localObject1, l4, this.assets[n].getDesiredMinimumQuantity());
/*     */             }
/*     */ 
/*     */           }
/*     */           catch (IllegalClientBidException localIllegalClientBidException8)
/*     */           {
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 447 */         IBid[] localObject3 = getAllSellBidsForMarket(localIMarketRepresentation.getMarketName());
/*     */ 
/* 449 */         if ((localObject3 != null) && (localObject3.length > 0))
/*     */         {
/* 455 */           IBid localObject41 = null;
/*     */ 
/* 457 */           for (int i12 = 0; i12 < localObject3.length; i12++) {
/* 458 */             IBid localObject5 = localObject3[i12];
/*     */ 
/* 461 */             if (localObject5.getGoodName().equals(localObject1))
/*     */             {
/* 464 */               if (this.HasOtherBuyers == "no")
/*     */               {
/* 467 */                 if (localObject41 == null) localObject41 = localObject5;
/* 468 */                 else if (((IBid)localObject41).getAmount() > localObject5.getAmount()) {
/* 469 */                   localObject41 = localObject5;
/*     */                 }
/*     */               }
/*     */ 
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/* 477 */           if (localObject41 != null)
/*     */           {
/*     */             try
/*     */             {
/* 481 */               if (((IBid)localObject41).getAmount() < 80L)
/*     */               {
/* 483 */                 submitBid(localIMarketRepresentation.getMarketName(), (String)localObject1, ((IBid)localObject41).getAmount(), this.assets[n].getDesiredMinimumQuantity());
/*     */               }
/*     */ 
/*     */             }
/*     */             catch (IllegalClientBidException localIllegalClientBidException10)
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
/* 499 */     for (int n = 0; n < this.assets.length; n++)
/*     */     {
/* 501 */       if ((this.assets[n].getGoodName().startsWith("ticket")) && (this.assets[n].getDesiredMinimumQuantity() == 0) && (this.assets[n].getQuantity() > 0))
/*     */       {
/* 505 */         long l3 = 100L;
/* 506 */         ArrayList localArrayList = new ArrayList();
/*     */ 
/* 508 */         localObject1 = this.assets[n].getGoodName();
/*     */ 
/* 512 */         localIMarketRepresentation = getMarket((String)localObject1);
/*     */ 
/* 514 */         arrayOfIBid3 = getAllSellBidsForMarket(localIMarketRepresentation.getMarketName());
/*     */         int i10;
/* 515 */         if ((arrayOfIBid3 != null) && (arrayOfIBid3.length > 0))
/*     */         {
/* 517 */           for (i10 = 0; i10 < arrayOfIBid3.length; i10++)
/*     */           {
/* 519 */             IBid localObject3 = arrayOfIBid3[i10];
/* 520 */             if ((localObject3).getGoodName().equals(localObject1))
/*     */             {
/* 522 */               localArrayList.add(Long.valueOf(((IBid)localObject3).getAmount()));
/*     */             }
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 528 */         if (localArrayList.size() == 0)
/*     */         {
/* 531 */           l3 = 75L;
/*     */         }
/*     */         else
/*     */         {
/* 536 */           for (i10 = 0; i10 < localArrayList.size(); i10++)
/*     */           {
/* 538 */             if (l3 > Long.valueOf(localArrayList.get(i10).toString()).longValue())
/*     */             {
/* 540 */               l3 = Long.valueOf(localArrayList.get(i10).toString()).longValue();
/*     */             }
/*     */           }
/*     */ 
/* 544 */           l3 -= 5L;
/*     */         }
/*     */ 
/* 547 */         IBid[] arrayOfIBid5 = getOutstandingSellBids(localIMarketRepresentation.getMarketName(), (String)localObject1);
/*     */ 
/* 549 */         if (arrayOfIBid5 == null)
/*     */         {
/* 551 */           if (localIMarketRepresentation.isMarketOpen())
/*     */             try
/*     */             {
/* 554 */               if (l3 > 50L)
/*     */               {
/* 556 */                 submitBid(localIMarketRepresentation.getMarketName(), (String)localObject1, -l3, this.assets[n].getQuantity());
/*     */               }
/*     */             }
/*     */             catch (IllegalClientBidException localIllegalClientBidException9)
/*     */             {
/*     */             }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private IMarketRepresentation[] getBedMarkets()
/*     */   {
/* 575 */     ArrayList localArrayList = new ArrayList(10);
/*     */ 
/* 577 */     String str = "room";
/*     */ 
/* 580 */     for (int i = 0; i < this.mr.length; i++) {
/* 581 */       String[] arrayOfString = this.mr[i].getGoodsAllowedByName();
/* 582 */       for (int j = 0; j < arrayOfString.length; j++) {
/* 583 */         if (arrayOfString[j].equals(str)) {
/* 584 */           localArrayList.add(this.mr[i]);
/*     */         }
/*     */       }
/*     */     }
/* 588 */     return (IMarketRepresentation[])localArrayList.toArray(new IMarketRepresentation[0]);
/*     */   }
/*     */ 
/*     */   private IMarketRepresentation getMarket(String paramString)
/*     */   {
/* 596 */     IMarketRepresentation localIMarketRepresentation = null;
/*     */ 
/* 599 */     for (int i = 0; i < this.mr.length; i++) {
/* 600 */       String[] arrayOfString = this.mr[i].getGoodsAllowedByName();
/* 601 */       for (int j = 0; j < arrayOfString.length; j++) {
/* 602 */         if (arrayOfString[j].equals(paramString)) {
/* 603 */           localIMarketRepresentation = this.mr[i];
/* 604 */           break;
/*     */         }
/*     */       }
/* 607 */       if (localIMarketRepresentation != null) break;
/*     */     }
/* 609 */     return localIMarketRepresentation;
/*     */   }
/*     */ }

/* Location:           D:\Profiles\fituser\Desktop\samples\
 * Qualified Name:     agent14
 * JD-Core Version:    0.6.2
 */