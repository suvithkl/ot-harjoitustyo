package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }

    @Test
    public void kortinSaldoAlussaOikein() {
        assertTrue(kortti.saldo()==10);
    }

    @Test
    public void rahanLataaminenKasvattaaSaldoaOikein() {
        kortti.lataaRahaa(18);
        assertEquals("saldo: 0.28", kortti.toString());
    }

    @Test
    public void saldoVaheneeOikeinJosTarpeeksiSaldoa() {
        kortti.otaRahaa(8);
        assertTrue(kortti.saldo()==2);
    }

    @Test
    public void salsoEiMuutuJosEiTarpeeksiSaldoa() {
        kortti.otaRahaa(13);
        assertTrue(kortti.saldo()==10);
    }

    @Test
    public void rahaRiittaaPalautetaanTrue() {
        assertTrue(kortti.otaRahaa(5)==true);
    }

    @Test
    public void rahaEiRiitaPalautetaanFalse() {
        assertTrue(kortti.otaRahaa(18)==false);
    }
}
