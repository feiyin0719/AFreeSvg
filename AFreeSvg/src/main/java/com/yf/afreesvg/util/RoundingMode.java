/*
 * Copyright (c) 2022.  by iffly Limited.  All rights reserved.
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 */

package com.yf.afreesvg.util;

public enum RoundingMode {
  CONSERVATIVE {
    @Override
    public boolean acceptUpperBound(boolean even) {
      return false;
    }

    @Override
    public boolean acceptLowerBound(boolean even) {
      return false;
    }
  },
  ROUND_EVEN {
    @Override
    public boolean acceptUpperBound(boolean even) {
      return even;
    }

    @Override
    public boolean acceptLowerBound(boolean even) {
      return even;
    }
  };

  public abstract boolean acceptUpperBound(boolean even);
  public abstract boolean acceptLowerBound(boolean even);
}