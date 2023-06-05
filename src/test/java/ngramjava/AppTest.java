/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package ngramjava;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.ngram.NGramTokenizer;
import org.apache.lucene.analysis.ro.RomanianAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.jupiter.api.Test;
import org.tartarus.snowball.ext.EnglishStemmer;
import org.tartarus.snowball.ext.RomanianStemmer;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class AppTest {


    @Test
    void indexAndSearch() throws IOException {
        final String text = "O pisică prinsese un liliac ce căzuse jos din zbor și vrînd să-l mînînce, el o ruga de iertămciune; iar mîța-i zise că nu poate să-l lase, fiindcă așa îi este meșteșugul, ca să prindă paseri. Liliacul îi zise atunci că el nu e pasere, ci șoarece ; și fu lăsat. După aceea iarăși fu prins de altă pisică, ce de asemenea vru să-l mînînce ; el iarăși o rugă să nu-l mînînce, că nu-i șoarece, ci-i pasere ; și iarăși îl lasă ; și așa se întîmplă că schimbîndu-și numele de două ori a scăpat cu viață. " +
                "Învățătură: Aceasta ne arată că se cuvine și nouă să nu rămînem la aceeași stare, ci să urmăm celor care se prefac după vremi și de multe ori scapă din primejdii.";


        String indexPath = "/tmp/tmpindex";
        Directory indexdir = FSDirectory.open(Paths.get(indexPath));
        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        IndexWriter writer = new IndexWriter(indexdir, iwc);

        Document doc = new Document();

        Field pathField = new StringField("source", "memory", Field.Store.YES);
        doc.add(pathField);
        final TextField textField = new TextField(
                "contents",
                new StringReader(text));
        doc.add(textField);

        writer.addDocument(doc);

    }


    @Test
    void englishStemmer() {
//        PorterStemmer stem = new PorterStemmer();
        EnglishStemmer stem = new EnglishStemmer();
        stem.setCurrent("given");
        stem.stem();
        String result = stem.getCurrent();
        System.out.println(result);
    }

    @Test
    void romStemmer() {
//        PorterStemmer stem = new PorterStemmer();
        RomanianStemmer stem = new RomanianStemmer();
        stem.setCurrent("reface");
        stem.stem();
        String result = stem.getCurrent();
        System.out.println(result);
    }


    @Test
    void grams() throws IOException {
        final String text = "O pisică prinsese un liliac ce căzuse jos din zbor și vrînd să-l mînînce, el o ruga de iertămciune; iar mîța-i zise că nu poate să-l lase, fiindcă așa îi este meșteșugul, ca să prindă paseri. Liliacul îi zise atunci că el nu e pasere, ci șoarece ; și fu lăsat. După aceea iarăși fu prins de altă pisică, ce de asemenea vru să-l mînînce ; el iarăși o rugă să nu-l mînînce, că nu-i șoarece, ci-i pasere ; și iarăși îl lasă ; și așa se întîmplă că schimbîndu-și numele de două ori a scăpat cu viață. " +
                "Învățătură: Aceasta ne arată că se cuvine și nouă să nu rămînem la aceeași stare, ci să urmăm celor care se prefac după vremi și de multe ori scapă din primejdii.";
        
        final int start = 1;
        final int end = 5;

        final Analyzer analyzer = new RomanianAnalyzer();
//        final TokenStream tokenStream = analyzer.tokenStream("pula", text);

        TokenStream stream = analyzer.tokenStream(null, new StringReader(text));
        CharTermAttribute cattr = stream.addAttribute(CharTermAttribute.class);
        stream.reset();
        while (stream.incrementToken()) {
            System.out.println(cattr.toString());
        }
        stream.end();
        stream.close();

//        final NGramTokenFilter nGramTokenFilter  = new NGramTokenFilter(tokenStream, 3); //nGramTokenizer = new NGramTokenizer(start, end);
//        ArrayList<String> tokens = new ArrayList<>();
//        try {
//
//            tokenStream.
//
//            nGramTokenizer.setReader(new StringReader(value));
//            final CharTermAttribute charTermAttribute = nGramTokenizer.addAttribute(CharTermAttribute.class);
//            nGramTokenizer.reset();
//            while (nGramTokenizer.incrementToken()) {
//                tokens.add(charTermAttribute.toString());
//            }
//            nGramTokenizer.end();
//            nGramTokenizer.close();
//        } catch (final IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println(tokens);
    }

    @Test void appHasAGreeting() {
        App classUnderTest = new App();
        assertNotNull(classUnderTest.getGreeting(), "app should have a greeting");
        final NGramTokenizer tokenizer = new NGramTokenizer();

        String s = "faoeie verde";

        final Analyzer analyzer = new StandardAnalyzer();
        final TokenStream tokenStream = analyzer.tokenStream("qwe", s);
//        tokenStream.


    }
}
