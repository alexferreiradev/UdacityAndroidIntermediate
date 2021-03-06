package com.alex.popularmovies.app.util;

import org.junit.Test;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by alex on 02/06/18.
 */
public class MoviesUtilTest {
	@Test
	public void formatDate() throws Exception {
		String date = MoviesUtil.formatDate(new Date(1528193618346L));

		assertEquals("Terça-feira, 5 de Junho de 2018", date);
	}

	@Test
	public void formatDate1() throws Exception {
		Date date = new Date();
		date.setTime(1527908400000L);
		assertEquals("Sábado, 2 de Junho de 2018", MoviesUtil.formatDate(date, SimpleDateFormat.FULL));
		assertEquals("02/06/18", MoviesUtil.formatDate(date, SimpleDateFormat.SHORT));
		assertEquals("02/06/2018", MoviesUtil.formatDate(date, SimpleDateFormat.MEDIUM));
		assertEquals("2 de Junho de 2018", MoviesUtil.formatDate(date, SimpleDateFormat.LONG));
	}

	@Test
	public void convertToDate() throws Exception {
		Date convertToDate = MoviesUtil.convertToDate("Sábado, 2 de Junho de 2018");
		assertEquals(1527908400000L, convertToDate.getTime());
		convertToDate = MoviesUtil.convertToDate("02/06/18", SimpleDateFormat.SHORT);
		assertEquals(1527908400000L, convertToDate.getTime());
		convertToDate = MoviesUtil.convertToDate("02/06/2018", SimpleDateFormat.MEDIUM);
		assertEquals(1527908400000L, convertToDate.getTime());
		convertToDate = MoviesUtil.convertToDate("2 de Junho de 2018", SimpleDateFormat.LONG);
		assertEquals(1527908400000L, convertToDate.getTime());
	}

	@Test
	public void readStream() throws Exception {
		InputStream stream = getClass().getResourceAsStream("/util/read_stream_example_file");
		String readFromStream = MoviesUtil.readStream(stream);

		assertNotNull(readFromStream);
		assertEquals("Teste de string from stream", readFromStream);
	}
}