/*
 * Axelor Business Solutions
 *
 * Copyright (C) 2020 Axelor (<http://axelor.com>).
 *
 * This program is free software: you can redistribute it and/or  modify
 * it under the terms of the GNU Affero General Public License, version 3,
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.axelor.apps.production.db.repo;

import com.axelor.apps.production.service.ProductProductionServiceImpl;
import com.axelor.apps.stock.db.StockMoveLine;
import com.axelor.apps.supplychain.db.repo.StockMoveLineSupplychainRepository;
import com.axelor.exception.service.TraceBackService;
import com.axelor.inject.Beans;

public class StockMoveLineProductionRepository extends StockMoveLineSupplychainRepository {

  @Override
  public StockMoveLine copy(StockMoveLine entity, boolean deep) {
    StockMoveLine copy = super.copy(entity, deep);
    if (!deep) {
      copy.setProducedManufOrder(null);
      copy.setConsumedManufOrder(null);
      copy.setConsumedOperationOrder(null);
    }
    return copy;
  }

  @Override
  public StockMoveLine save(StockMoveLine stockMoveLine) {
    stockMoveLine = super.save(stockMoveLine);
    try {
      Beans.get(ProductProductionServiceImpl.class)
          .computeTradingNamesCostPrice(
              stockMoveLine.getProduct(), stockMoveLine.getStockMove().getCompany());

    } catch (Exception e) {
      TraceBackService.trace(e);
    }
    return stockMoveLine;
  }
}
