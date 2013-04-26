/*     */ import emarket.client.AbstractClientAgent;
/*     */ import emarket.client.IllegalCancelBidException;
/*     */ import emarket.client.IllegalClientBidException;
/*     */ import emarket.client.interfaces.IAsset;
/*     */ import emarket.client.interfaces.IBid;
/*     */ import emarket.client.interfaces.IMarketRepresentation;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class agent11 extends AbstractClientAgent
/*     */ {
/*     */   private IMarketRepresentation[] mr;
/*     */   private IAsset[] assets;
/*  27 */   private boolean IHaveMyTravelTickets = false;
/*     */ 
/*  30 */   private boolean IHaveMyRoomsBooked = false;
/*     */ 
/*  33 */   int totalBedsBiddedForThisRound = 0;
/*     */ 
/*  36 */   int num = 0;
/*     */ 
/*  39 */   int flag = 1;
/*     */ 
/*     */   public void runAgentTasks()
/*     */   {
/*  50 */     IMarketRepresentation localIMarketRepresentation = null;
/*     */ 
/*  53 */     this.mr = getNoticeBoardDetails();
/*  54 */     this.assets = getAssets();
/*     */ 
/*  58 */     String str1 = "flight to melbourne";
/*     */ 
/*  63 */     String str2 = "room";
/*     */ 
				//Buying flight ticket
/*  72 */     localIMarketRepresentation = getMarket(str1);
/*     */ 
/*  81 */     for (int i = 0; i < this.assets.length; i++) {
/*  82 */       if (this.assets[i].getGoodName().equals(str1)) {
/*  83 */         if (this.assets[i].getQuantity() != this.assets[i].getDesiredMinimumQuantity()) break;
/*  84 */         this.IHaveMyTravelTickets = true; break;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  92 */     if ((localIMarketRepresentation.isMarketOpen()) && (!this.IHaveMyTravelTickets))
/*     */     {
/*  95 */       if (getOutstandingBuyBids(localIMarketRepresentation.getMarketName()) == null)
/*     */       {
/*     */         try
/*     */         {
/* 100 */           submitBid(localIMarketRepresentation.getMarketName(), str1, 400L, 8);
/*     */         }
/*     */         catch (IllegalClientBidException localIllegalClientBidException1) {
/* 103 */           localIllegalClientBidException1.printStackTrace();
/*     */         }
/*     */ 
/*     */       }
/* 114 */       else if (getCurrentTime() > 90L)
/*     */       {
/* 116 */         IBid[] arrayOfIBid1 = getOutstandingBuyBids(localIMarketRepresentation.getMarketName());
/* 117 */         long l = arrayOfIBid1[0].getAmount() + 20L;
/*     */ 
/* 125 */         for (int n = 0; n < arrayOfIBid1.length; n++)
/*     */         {
/* 127 */           if (localIMarketRepresentation.getAction().equals("Submit and Cancel")) {
/*     */             try
/*     */             {
/* 130 */               cancelBid(arrayOfIBid1[n]);
/*     */             }
/*     */             catch (IllegalCancelBidException localIllegalCancelBidException) {
/* 133 */               localIllegalCancelBidException.printStackTrace();
/*     */             }
/*     */           }
/*     */         }
/*     */ 
/*     */         try
/*     */         {
/* 140 */           submitBid(localIMarketRepresentation.getMarketName(), str1, l, 8);
/*     */         }
/*     */         catch (IllegalClientBidException localIllegalClientBidException2) {
/* 143 */           localIllegalClientBidException2.printStackTrace();
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }

				//Buying voucher for hotel
/*     */ 
/* 158 */     for (int j = 0; j < this.assets.length; j++) {
/* 159 */       if ((this.assets[j].getGoodName().equals(str2)) && (this.assets[j].getQuantity() >= this.assets[j].getDesiredMinimumQuantity()))
/*     */       {
/* 161 */         this.IHaveMyRoomsBooked = true;
/*     */       }
/*     */ 
/* 165 */       if (this.assets[j].getGoodName().equals(str2)) {
/* 166 */         this.num = this.assets[j].getQuantity();
/*     */       }
/*     */ 
/*     */     }

/*     */ 
/* 171 */     if (!this.IHaveMyRoomsBooked)
/*     */     {
/* 175 */       IMarketRepresentation[] localObject1 = getBedMarkets();
/*     */ 
/* 178 */       for (int k = 0; k < localObject1.length; k++)
/*     */       {
/* 180 */         localIMarketRepresentation = localObject1[k];
/*     */         
/* 192 */         if ((localIMarketRepresentation.isMarketOpen()) && (getCurrentTime() == localIMarketRepresentation.getClosingTime() - 1L)) //Do nothing until the last second of each sale
/*     */         {
/* 194 */           this.totalBedsBiddedForThisRound += localIMarketRepresentation.getQuantityGoodsOffered();
/*     */ 
/* 197 */           int m = 0;
/* 198 */           m = localIMarketRepresentation.getQuantityGoodsOffered();
/*     */ 
/* 202 */           if (this.flag == 1)
/*     */           {
/* 204 */             if (localIMarketRepresentation.getQuantityGoodsOffered() >= 15)
/*     */             {
/*     */               try
/*     */               {
/* 213 */                 submitBid(localIMarketRepresentation.getMarketName(), str2, 150L, 6);
/*     */               }
/*     */               catch (IllegalClientBidException localIllegalClientBidException3)
/*     */               {
/* 217 */                 localIllegalClientBidException3.printStackTrace();
/*     */               }
/*     */             }
/* 220 */             this.flag += 1;
/*     */           }
/* 223 */           else if (this.flag == 2)
/*     */           {
/*     */             try
/*     */             {
/* 229 */               submitBid(localIMarketRepresentation.getMarketName(), str2, 110L, m < 24 - this.num ? m : 24 - this.num);
/*     */             }
/*     */             catch (IllegalClientBidException localIllegalClientBidException4)
/*     */             {
/* 233 */               localIllegalClientBidException4.printStackTrace();
/*     */             }
/* 235 */             this.flag += 1;
/*     */           }
/* 240 */           else if (this.flag == 3)
/*     */           {
/*     */             try
/*     */             {
/* 245 */               submitBid(localIMarketRepresentation.getMarketName(), str2, 105L, m < 24 - this.num ? m : 24 - this.num);
/*     */             }
/*     */             catch (IllegalClientBidException localIllegalClientBidException5)
/*     */             {
/* 249 */               localIllegalClientBidException5.printStackTrace();
/*     */             }
/* 251 */             this.flag += 1;
/*     */           }
/*     */           else
/*     */           {
/*     */             try
/*     */             {
/* 258 */               if (this.num < 24)
/* 259 */                 submitBid(localIMarketRepresentation.getMarketName(), str2, 105L, m < 24 - this.num ? m : 24 - this.num);
/*     */             }
/*     */             catch (IllegalClientBidException localIllegalClientBidException6)
/*     */             {
/* 263 */               localIllegalClientBidException6.printStackTrace();
/*     */             }
/* 265 */             this.flag += 1;
/* 266 */             break;
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 284 */     

/*     */ 		//Buying entertainment tickets

/* 291 */     for (int k = 0; k < this.assets.length; k++)
/*     */     {
/* 295 */       if ((this.assets[k].getGoodName().startsWith("ticket")) && (this.assets[k].getDesiredMinimumQuantity() > 0) && (this.assets[k].getQuantity() < this.assets[k].getDesiredMinimumQuantity()))
/*     */       {
/* 300 */         String localObject1 = this.assets[k].getGoodName();
/*     */ 
/* 302 */         localIMarketRepresentation = getMarket(localObject1);
/*     */ 
/* 304 */         IBid[] arrayOfIBid2 = getAllSellBidsForMarket(localIMarketRepresentation.getMarketName());
/*     */ 
/* 306 */         if ((arrayOfIBid2 != null) && (arrayOfIBid2.length > 0))			//Find the cheapest bid
/*     */         {
/* 313 */           IBid localObject2 = null;
/* 314 */           for (int i1 = 0; i1 < arrayOfIBid2.length; i1++) {
/* 315 */             IBid localIBid = arrayOfIBid2[i1];
/* 316 */             if (localIBid.getGoodName().equals(localObject1)) {
/* 317 */               if (localObject2 == null) localObject2 = localIBid;
/* 318 */               else if (localObject2.getAmount() > localIBid.getAmount()) {
/* 319 */                 localObject2 = localIBid;
/*     */               }
/*     */ 
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/* 328 */           if ((localObject2 != null) && (localObject2.getAmount() < 100L)) {
/*     */             try {
/* 330 */               submitBid(localIMarketRepresentation.getMarketName(), (String)localObject1, 99L, this.assets[k].getDesiredMinimumQuantity());
/*     */             } catch (IllegalClientBidException localIllegalClientBidException9) {
/* 332 */               System.out.println("Failed: reason=" + localIllegalClientBidException9.getReason());
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
			
				//Selling entertainment tickets
/* 343 */     for (int k = 0; k < this.assets.length; k++)
/* 344 */       if ((this.assets[k].getGoodName().startsWith("ticket")) && (this.assets[k].getDesiredMinimumQuantity() == 0) && (this.assets[k].getQuantity() > 0)) {
/* 345 */         String localObject1 = this.assets[k].getGoodName();
/* 346 */         localIMarketRepresentation = getMarket(localObject1);
/*     */ 
/* 348 */         IBid[] arrayOfIBid2 = getOutstandingSellBids(localIMarketRepresentation.getMarketName(), localObject1);
/*     */ 
/* 352 */         if ((arrayOfIBid2 == null) && (localIMarketRepresentation.isMarketOpen()))
/*     */         {
/*     */           try
/*     */           {
/* 363 */             submitBid(localIMarketRepresentation.getMarketName(), localObject1, -9999L, this.assets[k].getQuantity());
/*     */           }
/*     */           catch (IllegalClientBidException localIllegalClientBidException7) {
/* 366 */             System.out.println("Failed: reason=" + localIllegalClientBidException7.getReason());
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 374 */         if (getCurrentTime() == 99L)
/*     */         {
/* 376 */           if ((arrayOfIBid2 != null) && (localIMarketRepresentation.isMarketOpen()))
/*     */             try
/*     */             {
/* 379 */               submitBid(localIMarketRepresentation.getMarketName(), (String)localObject1, -99L, this.assets[k].getQuantity());
/*     */             }
/*     */             catch (IllegalClientBidException localIllegalClientBidException8) {
/* 382 */               System.out.println("Failed: reason=" + localIllegalClientBidException8.getReason());
/*     */             }
/*     */         }
/*     */       }
/*     */   }
/*     */ 
/*     */   private IMarketRepresentation[] getBedMarkets()
/*     */   {
/* 396 */     ArrayList localArrayList = new ArrayList(10);
/*     */ 
/* 398 */     String str = "room";
/*     */ 
/* 401 */     for (int i = 0; i < this.mr.length; i++) {
/* 402 */       String[] arrayOfString = this.mr[i].getGoodsAllowedByName();
/* 403 */       for (int j = 0; j < arrayOfString.length; j++) {
/* 404 */         if (arrayOfString[j].equals(str)) {
/* 405 */           localArrayList.add(this.mr[i]);
/*     */         }
/*     */       }
/*     */     }
/* 409 */     return (IMarketRepresentation[])localArrayList.toArray(new IMarketRepresentation[0]);
/*     */   }
/*     */ 
/*     */   private IMarketRepresentation getMarket(String paramString)
/*     */   {
/* 417 */     IMarketRepresentation localIMarketRepresentation = null;
/*     */ 
/* 420 */     for (int i = 0; i < this.mr.length; i++) {
/* 421 */       String[] arrayOfString = this.mr[i].getGoodsAllowedByName();
/* 422 */       for (int j = 0; j < arrayOfString.length; j++) {
/* 423 */         if (arrayOfString[j].equals(paramString)) {
/* 424 */           localIMarketRepresentation = this.mr[i];
/* 425 */           break;
/*     */         }
/*     */       }
/* 428 */       if (localIMarketRepresentation != null) break;
/*     */     }
/* 430 */     return localIMarketRepresentation;
/*     */   }
/*     */ }

/* Location:           D:\Profiles\fituser\Desktop\samples\
 * Qualified Name:     agent11
 * JD-Core Version:    0.6.2
 */