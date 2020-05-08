package com.example.bsport;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SportFacFragmentTest {
    @Test
    public void descriptionEmpty_oneTest_ReturnTrue() {
        assertTrue(SportFacFragment.Checking_description_Empty(""));
    }

    @Test
    public void descriptionEmpty_oneTest_assertFalse() {
        assertFalse(SportFacFragment.Checking_description_Empty("1. משחק כדורסל"));
    }

    @Test
    public void descriptionEmpty_twoTest_assertFalse() {
        assertFalse(SportFacFragment.Checking_description_Empty("football 5X5"));
    }

    @Test
    public void Checking_number_of_players_Empty_oneTest_ReturnTrue() {
        assertTrue(SportFacFragment.Checking_number_of_players_Empty(""));
    }

    @Test
    public void Checking_number_of_players_Empty_oneTest_assertFalse() {
        assertFalse(SportFacFragment.Checking_description_Empty("football 5X5"));
    }

    @Test
    public void Checking_number_of_players_Empty_twoTest_assertFalse() {
        assertFalse(SportFacFragment.Checking_description_Empty("4"));
    }

    @Test
    public void Checking_number_of_players_valid_value_oneTest_ReturnTrue() {
        assertTrue(SportFacFragment.Checking_number_of_players_valid_value("football"));
    }
    @Test
    public void Checking_number_of_players_valid_value_oneTest_assertFalse() {
        assertFalse(SportFacFragment.Checking_number_of_players_valid_value("5"));
    }
    @Test
    public void Checking_game_date_Empty_oneTest_ReturnTrue() {
        assertTrue(SportFacFragment.Checking_game_date_Empty(""));
    }
    @Test
    public void Checking_game_date_Empty_oneTest_assertFalse() {
        assertFalse(SportFacFragment.Checking_game_date_Empty("5"));
    }
    @Test
    public void Checking_game_date_Empty_twoTest_assertFalse() {
        assertFalse(SportFacFragment.Checking_game_date_Empty("30-02-2020"));
    }
    @Test
    public void Checking_game_date_valid_value_oneTest_ReturnTrue() {
        assertTrue(SportFacFragment.Checking_game_date_valid_value(""));
    }
    @Test
    public void Checking_game_date_valid_value_twoTest_ReturnTrue() {
        assertTrue(SportFacFragment.Checking_game_date_valid_value("30/02/2020"));
    }
    @Test
    public void Checking_game_date_valid_value_oneTest_assertFalse() {
        assertFalse(SportFacFragment.Checking_game_date_valid_value("30-02-2020"));
    }
    @Test
    public void Check_that_date_after_today_oneTest_ReturnTrue() {
        assertTrue(SportFacFragment.Check_that_date_after_today("03-05-2020","04-05-2020"));
    }
    @Test
    public void Check_that_date_after_today_oneTest_assertFalse() {
        assertFalse(SportFacFragment.Check_that_date_after_today("03-05-2020","02-05-2020"));
    }
}
