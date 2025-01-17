// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

package org.apache.doris.nereids.rules.implementation;

import org.apache.doris.nereids.PlannerContext;
import org.apache.doris.nereids.memo.Group;
import org.apache.doris.nereids.rules.Rule;
import org.apache.doris.nereids.trees.plans.GroupPlan;
import org.apache.doris.nereids.trees.plans.Plan;
import org.apache.doris.nereids.trees.plans.PlanType;
import org.apache.doris.nereids.trees.plans.logical.LogicalLimit;

import mockit.Mocked;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class LogicalLimitToPhysicalLimitTest {
    @Test
    public void toPhysicalLimitTest(@Mocked Group group, @Mocked PlannerContext plannerContext) {
        Plan logicalPlan = new LogicalLimit<>(3, 4, new GroupPlan(group));
        Rule rule = new LogicalLimitToPhysicalLimit().build();
        List<Plan> physicalPlans = rule.transform(logicalPlan, plannerContext);
        Assertions.assertEquals(1, physicalPlans.size());
        Plan impl = physicalPlans.get(0);
        Assertions.assertEquals(PlanType.PHYSICAL_LIMIT, impl.getType());
    }
}
