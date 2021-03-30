package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class KassapaateTest {

    Kassapaate kassa;
    Maksukortti kortti;
    Maksukortti kortti2;

    @Before
    public void setUp() {
        kassa = new Kassapaate();
        kortti = new Maksukortti(500);
        kortti2 = new Maksukortti(100);
    }

    @Test
    public void luodunKassanRahatOikein() {
        assertTrue(kassa.kassassaRahaa()==100000);
    }

    @Test
    public void luodunKassanEdullisetMyynnitOikein() {
        assertTrue(kassa.edullisiaLounaitaMyyty()==0);
    }

    @Test
    public void luodunKassanMaukkaatMyynnitOikein() {
        assertTrue(kassa.maukkaitaLounaitaMyyty()==0);
    }

    @Test
    public void edullinenOstoKasvattaaKassaaOikein() {
        kassa.syoEdullisesti(300);
        assertTrue(kassa.kassassaRahaa()==100240);
    }

    @Test
    public void edullinenOstoVaihtorahaOikein() {
        assertTrue(kassa.syoEdullisesti(300)==60);
    }

    @Test
    public void maukasOstoKasvattaaKassaaOikein() {
        kassa.syoMaukkaasti(450);
        assertTrue(kassa.kassassaRahaa()==100400);
    }

    @Test
    public void maukasOstoVaihtorahaOikein() {
        assertTrue(kassa.syoMaukkaasti(450)==50);
    }

    @Test
    public void edullinenOstoMyydytLounaatKasvavat() {
        kassa.syoEdullisesti(240);
        assertTrue(kassa.edullisiaLounaitaMyyty()==1);
    }

    @Test
    public void maukasOstoMyydytLounaatKasvavat() {
        kassa.syoMaukkaasti(400);
        assertTrue(kassa.maukkaitaLounaitaMyyty()==1);
    }

    @Test
    public void rahaEiRiitaEdulliseenKassaEiMuutu() {
        kassa.syoEdullisesti(100);
        assertTrue(kassa.kassassaRahaa()==100000);
    }

    @Test
    public void rahaEiRiitaEdulliseenPalautusOikein() {
        assertTrue(kassa.syoEdullisesti(100)==100);
    }

    @Test
    public void rahaEiRiitaEdulliseenMyydytLounaatEiKasva() {
        kassa.syoEdullisesti(100);
        assertTrue(kassa.edullisiaLounaitaMyyty()==0);

    }

    @Test
    public void rahaEiRiitaMaukkaaseenKassaEiMuutu() {
        kassa.syoMaukkaasti(300);
        assertTrue(kassa.kassassaRahaa()==100000);
    }

    @Test
    public void rahaEiRiitaMaukkaaseenPalautusOikein() {
        assertTrue(kassa.syoMaukkaasti(300)==300);
    }

    @Test
    public void rahaEiRiitaMaukkaaseenMyydytLounaatEiKasva() {
        kassa.syoMaukkaasti(300);
        assertTrue(kassa.maukkaitaLounaitaMyyty()==0);
    }

    @Test
    public void edullisenKorttiostoVeloittaaOikein() {
        kassa.syoEdullisesti(kortti);
        assertTrue(kortti.saldo()==260);
    }

    @Test
    public void edullisenKorttiostoPalautetaanTrue() {
        assertTrue(kassa.syoEdullisesti(kortti)==true);
    }

    @Test
    public void maukkaanKorttiostoVeloittaaOikein() {
        kassa.syoMaukkaasti(kortti);
        assertTrue(kortti.saldo()==100);
    }

    @Test
    public void maukkaanKorttiostoPalautetaanTrue() {
        assertTrue(kassa.syoMaukkaasti(kortti)==true);
    }

    @Test
    public void edullisenKorttiostoMyydytLounaatKasvavat() {
        kassa.syoEdullisesti(kortti);
        assertTrue(kassa.edullisiaLounaitaMyyty()==1);
    }

    @Test
    public void maukkaanKorttiostoMyydytLounaatKasvavat() {
        kassa.syoMaukkaasti(kortti);
        assertTrue(kassa.maukkaitaLounaitaMyyty()==1);
    }

    @Test
    public void eiRahaaEdullisenKorttiostoonKorttiaEiVeloiteta() {
        kassa.syoEdullisesti(kortti2);
        assertTrue(kortti2.saldo()==100);
    }

    @Test
    public void eiRahaaEdullisenKorttiostoonMyydytLounaatEiKasva() {
        kassa.syoEdullisesti(kortti2);
        assertTrue(kassa.edullisiaLounaitaMyyty()==0);
    }

    @Test
    public void eiRahaaEdullisenKorttiostoonPalautetaanFalse() {
        assertTrue(kassa.syoEdullisesti(kortti2)==false);
    }

    @Test
    public void eiRahaaMaukkaanKorttiostoonKorttiaEiVeloiteta() {
        kassa.syoMaukkaasti(kortti2);
        assertTrue(kortti2.saldo()==100);
    }

    @Test
    public void eiRahaaMaukkaanKorttiostoonMyydytLounaatEiKasva() {
        kassa.syoMaukkaasti(kortti2);
        assertTrue(kassa.maukkaitaLounaitaMyyty()==0);
    }

    @Test
    public void eiRahaaMaukkaanKorttiostoonPalautetaanFalse() {
        assertTrue(kassa.syoMaukkaasti(kortti2)==false);
    }

    @Test
    public void edullisenKorttiostoKassanRahatEiMuutu() {
        kassa.syoEdullisesti(kortti);
        assertTrue(kassa.kassassaRahaa()==100000);
    }

    @Test
    public void maukkaanKorttiostoKassanRahatEiMuutu() {
        kassa.syoMaukkaasti(kortti);
        assertTrue(kassa.kassassaRahaa()==100000);
    }

    @Test
    public void kortinSaldoMuuttuuOikeinRahaaLadattaessa() {
        kassa.lataaRahaaKortille(kortti, 300);
        assertTrue(kortti.saldo()==800);
    }

    @Test
    public void kortilleEiVoiLadataNegatiivistaSummaa() {
        kassa.lataaRahaaKortille(kortti, -100);
        assertTrue(kortti.saldo()==500);
    }

    @Test
    public void rahanLatausKortilleKassaMuuttuuOikein() {
        kassa.lataaRahaaKortille(kortti, 300);
        assertTrue(kassa.kassassaRahaa()==100300);
    }
}