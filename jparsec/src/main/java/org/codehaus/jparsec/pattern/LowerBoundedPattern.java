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

class LowerBoundedPattern extends Pattern {
  private static final transient Logger log = LoggerFactory.getLogger(Patterns.class);

  private final int min;
  private final Pattern pattern;

  LowerBoundedPattern(int min, Pattern pattern) {
    this.min = min;
    this.pattern = pattern;
  }

  @Override public int match(CharSequence src, int begin, int end) {
    log.trace("[LB={}] matching pattern \"{}\" at \"{}\"",min,pattern.toString(),src.subSequence(begin,end).toString().replace("\n","\\n"));
    int minLen = RepeatPattern.matchRepeat(min, pattern, src, end, begin, 0);
    if (MISMATCH == minLen) return MISMATCH;
    log.trace("[LB={}] minLen={}",min,minLen);
    return ManyPattern.matchMany(pattern, src, end, begin + minLen, minLen);
  }

  @Override public String toString() {
    return (min > 1) ? (pattern + "{" + min + ",}") : (pattern + "+");
  }
}
