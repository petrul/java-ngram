package ngramjava

import org.junit.jupiter.api.Test
/**
 *
 */
class NGramStoreTest {
    
    @Test
    void testNgramStore() {

        def store = new NGramStore()

        store.put('hello', 'world');

        assert store.map.keySet().size() == 1
        assert store.map.containsKey('hello')

        Map values = store.map.get('hello')

        println values
        assert values.size() == 1
        assert values["world"] == 1

        store.put('hello', 'world')
        assert values.size() == 1
        assert values['world'] == 2

        store.put('salut', 'lume')
        assert values.size() == 1

        assert store.map.size() == 2
        assert store.map.containsKey('salut')
        assert store.map['salut']['lume'] == 1

        store.put('salut', 'mai')
        assert store.map.size() == 2
        assert store.map['salut'].size() == 2
    }

}