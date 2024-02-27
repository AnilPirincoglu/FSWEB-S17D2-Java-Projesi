package com.workintech.s17d2.model;

import com.workintech.s17d2.tax.Taxable;

public class DeveloperFactory {
    public static Developer createDeveloper(Developer developer, Taxable taxable) {

        Developer createDeveloper = null;

        switch (developer.getExperience()) {
            case JUNIOR:
                createDeveloper = new JuniorDeveloper(developer.getId(), developer.getName(),
                        netSalary(developer.getSalary(), taxable.getSimpleTaxRate()));
                break;
            case MID:
                createDeveloper = new MidDeveloper(developer.getId(), developer.getName(),
                        netSalary(developer.getSalary(), taxable.getMiddleTaxRate()));
                break;
            case SENIOR:
                createDeveloper = new SeniorDeveloper(developer.getId(), developer.getName(),
                        netSalary(developer.getSalary(), taxable.getUpperTaxRate()));
                break;
        }
        return createDeveloper;
    }

    private static Double netSalary(Double salary, Double taxRate) {
        return salary * (1 - taxRate);
    }


}
