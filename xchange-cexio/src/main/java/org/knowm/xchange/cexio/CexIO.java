package org.knowm.xchange.cexio;

import java.io.IOException;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.knowm.xchange.cexio.dto.marketdata.CexIOCurrencyLimits;
import org.knowm.xchange.cexio.dto.marketdata.CexIODepth;
import org.knowm.xchange.cexio.dto.marketdata.CexIOTicker;
import org.knowm.xchange.cexio.dto.marketdata.CexIOTickersResponse;
import org.knowm.xchange.cexio.dto.marketdata.CexIOTrade;

/** @author brox */
@Path("api")
@Produces(MediaType.APPLICATION_JSON)
public interface CexIO {

  /**
   * Gets all tickers for the CexIO market
   *
   * <p>At the moment CexIO has the following 5 markets: USD, EUR, GBP, RUB, BTC
   *
   * <p>We are hardcoding those 5 markets in the path since ResCU rest client does not support
   * passing an arbitrary number of optional path params.
   *
   * <p>If RecCU supported that then we would like to get rid of this method and create a method
   * with the following signature {@code getTickers(Set<String>)} where each string in the set will
   * be added as a path parameter
   *
   * <p>See <a href="https://github.com/mmazi/rescu/issues/110"
   * target="_top">https://github.com/mmazi/rescu/issues/110</a>
   *
   * @return The tickers response
   * @throws IOException
   */
  @GET
  @Path("tickers/USD/EUR/GBP/RUB/BTC")
  CexIOTickersResponse getAllTickers() throws IOException;

  @GET
  @Path("ticker/{ident}/{currency}")
  CexIOTicker getTicker(
      @PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency)
      throws IOException;

  @GET
  @Path("order_book/{ident}/{currency}")
  CexIODepth getDepth(
      @PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency)
      throws IOException;

  @GET
  @Path("trade_history/{ident}/{currency}/")
  CexIOTrade[] getTrades(
      @PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency)
      throws IOException;

  @POST
  @Path("trade_history/{ident}/{currency}/")
  CexIOTrade[] getTradesSince(
      @PathParam("ident") String tradeableIdentifier,
      @PathParam("currency") String currency,
      @DefaultValue("1") @FormParam("since") long since)
      throws IOException;

  @GET
  @Path("currency_limits")
  CexIOCurrencyLimits getCurrencyLimits() throws IOException;
}