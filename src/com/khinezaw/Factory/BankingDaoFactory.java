package com.khinezaw.Factory;

import com.khinezaw.BankingDaoImpl;
import com.khinezaw.Repository.BankingDao;

public class BankingDaoFactory {
    public static BankingDao dao;

    private BankingDaoFactory() {
    }

    public static BankingDao getBankingDao() {
        if (dao == null)
            dao = new BankingDaoImpl();
        return dao;
    }
}
