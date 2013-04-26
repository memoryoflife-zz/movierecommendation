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
/*     */ public class agent12 extends AbstractClientAgent
/*     */ {
/*  18 */   private int vouchers = 0;
/*     */   private IMarketRepresentation[] mr;
/*     */   private IAsset[] assets;
/*  26 */   private boolean IHaveMyTravelTickets = false;
/*     */ 
/*  28 */   private boolean IHaveMyRoomsBooked = false;
/*     */ 
/*     */   public void runAgentTasks()
/*     */   {
/*  40 */     IMarketRepresentation localIMarketRepresentation = null;
/*     */ 
/*  43 */     this.mr = getNoticeBoardDetails();
/*  44 */     this.assets = getAssets();
/*     */ 
/*  48 */     String str1 = "flight to melbourne";
/*     */ 
/*  53 */     String str2 = "room";

				//Code for buying flight ticket
/*     */ 
/*  61 */     IMarketHistoryBid[] arrayOfIMarketHistoryBid = getMarketHistoryBidsForMarket(getMarket(str1).getMarketName(), getCurrentTime() - 1L);
/*  62 */     if (arrayOfIMarketHistoryBid.length != 0)
/*     */     {
/*  68 */       for (int i = 0; i < arrayOfIMarketHistoryBid.length; i++);
/*     */     }
/*     */ 
/*  76 */     localIMarketRepresentation = getMarket(str1);
/*     */ 
/*  83 */     for (int i = 0; i < this.assets.length; i++) {
/*  84 */       if (this.assets[i].getGoodName().equals(str1))
/*     */       {
/*  86 */         if (this.assets[i].getQuantity() != this.assets[i].getDesiredMinimumQuantity()) break;
/*  87 */         this.IHaveMyTravelTickets = true; break;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  98 */     if ((localIMarketRepresentation.isMarketOpen()) && (!this.IHaveMyTravelTickets))
/*     */     {
/* 100 */       if (getCurrentTime() == 99L) {
/* 101 */         IBid[] arrayOfIBid = getOutstandingBuyBids(localIMarketRepresentation.getMarketName());
/* 102 */         for (int k = 0; k < arrayOfIBid.length; k++) 
				 {
/* 103 */           if (localIMarketRepresentation.getAction().equals("Submit and Cancel"))
/*     */             try {
/* 105 */               cancelBid(arrayOfIBid[k]);
/*     */             }
/*     */             catch (IllegalCancelBidException localIllegalCancelBidException) {
/* 108 */               localIllegalCancelBidException.printStackTrace();
/*     */             }
/*     */         }
/*     */         try
/*     */         {
/* 113 */           submitBid(localIMarketRepresentation.getMarketName(), str1, 600L, 8);
/*     */         } catch (IllegalClientBidException localIllegalClientBidException2) {
/* 115 */           localIllegalClientBidException2.printStackTrace();
/*     */         }
/*     */       }
/* 118 */       else if (getOutstandingBuyBids(localIMarketRepresentation.getMarketName()) == null)
/*     */       {
/*     */         try
/*     */         {
/* 123 */           submitBid(localIMarketRepresentation.getMarketName(), str1, 410L, 8);
/*     */         }
/*     */         catch (IllegalClientBidException localIllegalClientBidException1)
/*     */         {
/* 127 */           localIllegalClientBidException1.printStackTrace();
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
				//code for buying vouchers for hotel room
/* 137 */     for (int j = 0; j < this.assets.length; j++) {
/* 138 */       if ((this.assets[j].getGoodName().equals(str2)) && (this.assets[j].getQuantity() >= this.assets[j].getDesiredMinimumQuantity())) {
/* 139 */         this.IHaveMyRoomsBooked = true;
/*     */       }
/* 141 */       if (this.assets[j].getGoodName().equals(str2))
/*     */       {
/* 143 */         this.vouchers = this.assets[j].getQuantity();
/*     */       }
/*     */     }
/*     */    
/* 148 */     if (!this.IHaveMyRoomsBooked)
/*     */     {
/* 151 */       IMarketRepresentation[] localObject1 = getBedMarkets();
/*     */ 
/* 158 */       for (int m = 0; m < localObject1.length; m++)
/*     */       {
/* 162 */         IMarketHistoryBid[] localObject2 = getMarketHistoryBidsForMarket(localObject1[m].getMarketName(), getCurrentTime() - 1L);
/*     */         
/* 163 */         if (localObject2.length != 0)
/*     */         {
/* 170 */           for (int n = 0; n < localObject2.length; n++);
/*     */         }
/*     */ 
/* 176 */         localIMarketRepresentation = localObject1[m];
/* 177 */         if ((localIMarketRepresentation.isMarketOpen()) && (getCurrentTime() == localIMarketRepresentation.getClosingTime() - 1L)) {
/* 178 */           int num = localIMarketRepresentation.getQuantityGoodsOffered();
/*     */           try
/*     */           {
/* 186 */             submitBid(localIMarketRepresentation.getMarketName(), str2, 101L, num < 24 - this.vouchers ? num : 24 - this.vouchers);
/*     */           }
/*     */           catch (IllegalClientBidException localIllegalClientBidException5) {
/* 189 */             localIllegalClientBidException5.printStackTrace();
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }

/*     */ 
/* 202 */     
/*     */ 		//Buy entertainment tickets
/* 207 */     for (int m = 0; m < this.assets.length; m++)
/*     */     {
/* 210 */       if ((this.assets[m].getGoodName().startsWith("ticket")) && (this.assets[m].getDesiredMinimumQuantity() > 0) && (this.assets[m].getQuantity() < this.assets[m].getDesiredMinimumQuantity()))
/*     */       {
/* 215 */         String localObject1 = this.assets[m].getGoodName();
/*     */ 
/* 217 */         localIMarketRepresentation = getMarket(localObject1);
/*     */ 
/* 219 */         IBid[] localObject2 = getAllSellBidsForMarket(localIMarketRepresentation.getMarketName());
/*     */ 
/* 221 */         if ((localObject2 != null) && (localObject2.length > 0))
/*     */         {
/* 227 */           IBid localObject3 = null;
/* 228 */           for (int i1 = 0; i1 < localObject2.length; i1++) {
/* 229 */             IBid localObject4 = localObject2[i1];
/* 230 */             if (localObject4.getGoodName().equals(localObject1)) {
/* 231 */               if (localObject3 == null)
/* 232 */                 localObject3 = localObject4;
/* 233 */               else if (localObject3.getAmount() > localObject4.getAmount()) {
/* 234 */                 localObject3 = localObject4;
/*     */               }
/*     */ 
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/* 244 */           if ((localObject3 != null) && (localObject3.getAmount() < 100L)) {
/*     */             try {
/* 246 */               submitBid(localIMarketRepresentation.getMarketName(), localObject1, 99L, this.assets[m].getDesiredMinimumQuantity());
/*     */             } catch (IllegalClientBidException localIllegalClientBidException6) {
/* 248 */               System.out.println("TestClient1: submit failed: reason=" + localIllegalClientBidException6.getReason());
/*     */             }
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
					//Sell entertainment tickets
/* 257 */     for (int m = 0; m < this.assets.length; m++)
/* 258 */       if ((this.assets[m].getGoodName().startsWith("ticket")) && (this.assets[m].getDesiredMinimumQuantity() == 0) && (this.assets[m].getQuantity() > 0)) {
/* 259 */         String localObject1 = this.assets[m].getGoodName();
/* 260 */         localIMarketRepresentation = getMarket(localObject1);
/*     */ 
/* 262 */        IBid[] localObject2 = getOutstandingSellBids(localIMarketRepresentation.getMarketName(), (String)localObject1);
/*     */ 
/* 266 */         if ((localObject2 == null) && (localIMarketRepresentation.isMarketOpen()))
/*     */         {
/*     */           try
/*     */           {
/* 271 */             submitBid(localIMarketRepresentation.getMarketName(), (String)localObject1, -10000L, this.assets[m].getQuantity());
/*     */           }
/*     */           catch (IllegalClientBidException localIllegalClientBidException3) {
/* 274 */             System.out.println("TestClient1: submit2 failed: reason=" + localIllegalClientBidException3.getReason());
/*     */           }
/*     */         }
/*     */ 
/* 278 */         if ((getCurrentTime() == 98L) && (localIMarketRepresentation.isMarketOpen()) && (localObject2 != null))
/*     */           try {
/* 280 */             submitBid(localIMarketRepresentation.getMarketName(), localObject1, -99L, this.assets[m].getQuantity());
/*     */           }
/*     */           catch (IllegalClientBidException localIllegalClientBidException4) {
/* 283 */             System.out.println("TestClient1: submit2 failed: reason=" + localIllegalClientBidException4.getReason());
/*     */           }
/*     */       }
/*     */   }
/*     */ 
/*     */   private IMarketRepresentation[] getBedMarkets()
/*     */   {
/* 314 */     ArrayList localArrayList = new ArrayList(10);
/*     */ 
/* 316 */     String str = "room";
/*     */ 
/* 319 */     for (int i = 0; i < this.mr.length; 
/* 320 */       i++) {
/* 321 */       String[] arrayOfString = this.mr[i].getGoodsAllowedByName();
/* 322 */       for (int j = 0; j < arrayOfString.length; 
/* 323 */         j++) {
/* 324 */         if (arrayOfString[j].equals(str)) {
/* 325 */           localArrayList.add(this.mr[i]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 330 */     return (IMarketRepresentation[])localArrayList.toArray(new IMarketRepresentation[0]);
/*     */   }
/*     */ 
/*     */   private IMarketRepresentation getMarket(String paramString)
/*     */   {
/* 338 */     IMarketRepresentation localIMarketRepresentation = null;
/*     */ 
/* 341 */     for (int i = 0; i < this.mr.length; 
/* 342 */       i++) {
/* 343 */       String[] arrayOfString = this.mr[i].getGoodsAllowedByName();
/* 344 */       for (int j = 0; j < arrayOfString.length; 
/* 345 */         j++) {
/* 346 */         if (arrayOfString[j].equals(paramString)) {
/* 347 */           localIMarketRepresentation = this.mr[i];
/* 348 */           break;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 354 */       if (localIMarketRepresentation != null)
/*     */       {
/*     */         break;
/*     */       }
/*     */     }
/* 359 */     return localIMarketRepresentation;
/*     */   }
/*     */ }

/* Location:           D:\Profiles\fituser\Desktop\samples\
 * Qualified Name:     agent12
 * JD-Core Version:    0.6.2
 */