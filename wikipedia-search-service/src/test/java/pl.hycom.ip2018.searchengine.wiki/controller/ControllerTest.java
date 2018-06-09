package pl.hycom.ip2018.searchengine.wiki.controller;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pl.hycom.ip2018.searchengine.wiki.exception.WikipediaException;
import pl.hycom.ip2018.searchengine.wiki.service.WikiSearch;

public class ControllerTest {

    @Mock
    private WikiSearch wikiSearch;

    @InjectMocks
    private Controller wikiController;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        wikiController = new Controller();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCallGetResponseFromWikiOnceWithCorrectQuery() throws WikipediaException {
        // given
        final String query = "test";

        // when
        wikiController.getResponseFromWiki(query);

        // then
        Mockito.verify(wikiSearch, Mockito.times(1)).getResponse(query);
    }
}