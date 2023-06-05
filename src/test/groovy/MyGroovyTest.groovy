import ngramjava.NGramStore
import org.apache.lucene.analysis.Analyzer
import org.apache.lucene.analysis.LowerCaseFilter
import org.apache.lucene.analysis.TokenStream
import org.apache.lucene.analysis.shingle.ShingleAnalyzerWrapper
import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.analysis.standard.StandardTokenizer
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute
import org.apache.lucene.analysis.tokenattributes.TermFrequencyAttribute
import org.apache.lucene.analysis.tokenattributes.TypeAttribute
import org.junit.jupiter.api.Test

import java.util.function.Consumer
import java.util.zip.GZIPInputStream

class MyGroovyTest {

    @Test
    void test1() {

        final text = """
                    You just stood there screaming
                    Fearing no one was listening to you.
                    They say the empty can rattles the most
                    The sound of your voice must soothe you
                    Hearing only what you want to hear
                    And knowing only what you've heard
                    You you're smothered in tragedy
                    And you're up to save the world
                    Misery you insist that the weight of the world
                    Should be on your shoulders
                    Misery there's much more to life than what you see
                    My friend of misery
                    You still stood there screaming
                    No one caring about these words you tell
                    My friend before your voice is gone
                    One man's fun is another's hell
                    These times are sent to try men's souls
                    But something's wrong with all you see
                    You, you'll take it on all yourself
                    Remember, misery loves company
                    Misery you insist that the weight of the world
                    Should be on your shoulders
                    Misery there's much more to life than what you see
                    My friend of misery (my friend of misery)
                    Misery you insist that the weight of the world
                    Should be on your shoulders
                    Misery there's much more to life than what you see
                    My friend of misery
                    You just stood there screaming, oh
                    My friend of misery, yeah, yeah, oh
                    """
        
        Analyzer a = new Analyzer() {
//            NGramTokenFilter nGramTokenFilter;

            @Override
            protected Analyzer.TokenStreamComponents createComponents(String fieldName) {
                def stdtok = new StandardTokenizer()
                def t = new LowerCaseFilter(stdtok)
//                t = new NGramTokenFilter(t, 3)
//                t = new ShingleFilter(t, 3, 3)
                
//                this.nGramTokenFilter = t

                new Analyzer.TokenStreamComponents((Reader reader) -> {
                    stdtok.reader = reader
                } as Consumer<Reader>, t)
            }
        }
        
        def shingleAnalyzer = new ShingleAnalyzerWrapper(new StandardAnalyzer(),
                2,
                2) //, '.', false, false, 'pula')

        def shakespReader = new BufferedReader(
                new InputStreamReader(
                        new GZIPInputStream(
                                this.class.classLoader.getResourceAsStream('100-0.txt.gz'))))

//        TokenStream stream = shingleAnalyzer.tokenStream(null, new StringReader(text))
        TokenStream stream = shingleAnalyzer.tokenStream(null, shakespReader)

        CharTermAttribute cattr = stream.addAttribute(CharTermAttribute.class)
        TermFrequencyAttribute tfattr = stream.addAttribute(TermFrequencyAttribute.class)
        TypeAttribute typeAttribute = stream.addAttribute(TypeAttribute.class)

        def ngramStore = new NGramStore()

        int ngramCounter = 0;
        stream.reset()
        while (stream.incrementToken()) {
            if (typeAttribute.type == 'shingle') {
                final crt = cattr.toString()
                final words = crt.split(' ')
                final first = words[0]
                final second = words[1]

                ngramStore.put(first, second)
                ngramCounter++;
            }
        }
        stream.end()
        stream.close()

        println(ngramStore.size())
//        println(ngramStore.map.keySet().sort())

        ngramStore.toJSON(new BufferedOutputStream(
                new FileOutputStream("/tmp/pula.json")))

    }

    @Test
    void readNgramstore() {
        def store = NGramStore.fromJSON(this.getClass().getClassLoader().getResourceAsStream("shakespeare-works-bigrams.json"));
        def storeSize = store.size()
        println storeSize
        
        def random = new Random()

        println "0 : " + store.map.findAll { k,v -> v.size() == 0}.size()
        println "1 : " + store.map.findAll { k,v -> v.size() == 1}.size()
        println "2 : " + store.map.findAll { k,v -> v.size() == 2}.size()
        println "3 : " + store.map.findAll { k,v -> v.size() == 3}.size()
        println "4 : " + store.map.findAll { k,v -> v.size() == 4}.size()

        int i = 0;
        def rnd = random.nextInt(storeSize)
        def word = store.map.keySet().getAt(rnd);

        while (i < 1000) {
            i++

            print word + " "
            def values = store.map[word]
            assert values.size() > 0

            rnd = random.nextInt(values.size())

            word = values.keySet().getAt(rnd);
            
        }
    }
}