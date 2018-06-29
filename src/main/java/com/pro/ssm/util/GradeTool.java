package com.pro.ssm.util;

import com.pro.ssm.model.custom.GradeInfo;

import java.util.List;

public class GradeTool {
    public static class GBA {
        public double GBA_sum;
        public double credit_sum;

        public double avgGBA() {
            return GBA_sum / credit_sum;
        }
    }

    public static GBA getGBA(List<GradeInfo> grades) {
        GBA gba = new GBA();
        gba.credit_sum = 0;
        gba.GBA_sum = 0;
        for (GradeInfo g : grades) {
            if (g.getGrade() == null)
                continue;
            gba.credit_sum += g.getCredit().doubleValue();
            gba.GBA_sum += g.getCredit().doubleValue() * (g.getGrade().doubleValue() - 50) / 10;
        }
        return gba;
    }
}
