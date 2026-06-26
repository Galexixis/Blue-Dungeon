package utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import blueDungeon.utils.Observable;
import blueDungeon.utils.ObserverManager;

/**
 * @author Galexis
 */
public class ObservableTest {

    private class ObserverTest{
         
        private int count = 1;

        public int getCount(){
            return count;
        }

        /**
         * En temps normale cette methode est imposé par une interface.
         * @param increment
         */
        public void update(int increment){
            count += increment;
        }
    }

    private class ObservableTester implements Observable<ObserverTest>{

        private int value = 1;
        private final ObserverManager<ObserverTest> observerManager = new ObserverManager<>();

        @Override
        public ObserverManager<ObserverTest> getObserverManager() {
            return observerManager;
        }

        public void add(){
            this.notifyObservers(e -> e.update(value));
            value++;
        }
    }

    @Test
    /**
     * Test l'interface Observable.
     */
    void observerTest(){

        ObserverTest observer1 = new ObserverTest();
        assertEquals(1 ,observer1.getCount(), "l'intialisation du test est mauvaise");

        ObservableTester tester = new ObservableTester();
        tester.addObserver(observer1);
        tester.add();
        assertEquals(2, observer1.getCount());

        tester.removeObserver(observer1);
        tester.add();
        assertEquals(2, observer1.getCount());

    }
    
}
