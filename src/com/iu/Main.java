package com.iu;

import co.paralleluniverse.galaxy.Grid;
import co.paralleluniverse.galaxy.Store;
import co.paralleluniverse.galaxy.StoreTransaction;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class Main {

    private static Logger logger;

    public static void main(String[] args) {
        logger = LogManager.getLogger(Main.class);
        logger.info("Hello");
        try {
            Grid grid = Grid.getInstance();
            grid.goOnline();
            Store store = grid.store();

            // store
            long root = -1;
            StoreTransaction txn = store.beginTransaction();
            try {
                root = store.getRoot("myRootName", txn);
                if (store.isRootCreated(root, txn))
                    store.set(root, "Hello Galaxy!".getBytes(), txn); // initialize root
                store.commit(txn);
                System.out.println("Store txn succeded.");
            } catch(Exception ex) {
                store.rollback(txn);
                store.abort(txn);
            }

            //read
            txn = store.beginTransaction();
            byte [] data = null;
            try {
                root = store.getRoot("myRootName", txn);
                if (store.isRootCreated(root, txn))
                    data = store.get(root);//.set(root, "Hello Galaxy!".getBytes(), txn); // initialize root
                store.commit(txn);
                System.out.println(Arrays.toString(data));
                System.out.println("Get txn succeded.");
            } catch(Exception ex) {
                store.rollback(txn);
                store.abort(txn);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
	// write your code here
    }
}
