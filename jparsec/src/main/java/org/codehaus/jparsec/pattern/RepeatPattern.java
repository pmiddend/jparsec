/*****************************************************************************
 * Copyright 2013 (C) Codehaus.org                                                *
 * ------------------------------------------------------------------------- *
 * Licensed under the Apache License, Version 2.0 (the "License");           *
 * you may not use this file except in compliance with the License.          *
 * You may obtain a copy of the License at                                   *
 *                                                                           *
 * http://www.apache.org/licenses/LICENSE-2.0                                *
 *                                                                           *
 * Unless required by applicable law or agreed to in writing, software       *
 * distributed under the License is distributed on an "AS IS" BASIS,         *
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *
 * See the License for the specific language governing permissions and       *
 * limitations under the License.                                            *
 *****************************************************************************/
package org.codehaus.jparsec.pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class RepeatPattern extends Pattern {
  private static final transient Logger log = LoggerFactory.getLogger(Patterns.class);

  private final int n;
  private final Pattern pattern;

  RepeatPattern(int n, Pattern pattern) {
    this.n = n;
    this.pattern = pattern;
  }

  @Override public int match(CharSequence src, int begin, int end) {
    return matchRepeat(n, pattern, src, end, begin, 0);
  }

  @Override public String toString() {
    return pattern.toString() + '{' + n + '}';
  }

  static int matchRepeat(int n, Pattern pattern, CharSequence src, int len, int from, int acc) {
    int end = from;
    for (int i = 0; i < n; i++) {
      log.trace("[RP|n={},p={}] i={}",n,pattern,i);
      int l = pattern.match(src, end, len);
      if (l == MISMATCH) {
        log.trace("[RP|n={},p={}] i={}, mismatch",n,pattern,i);
        return MISMATCH;
      }
      log.trace("[RP|n={},p={}] i={}, match",n,pattern,i);
      end += l;
    }
    log.trace("[RP|n={},p={}] result {}",n,pattern,end - from + acc);
    return end - from + acc;
  }
}
