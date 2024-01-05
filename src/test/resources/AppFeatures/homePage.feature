Feature: Web Scraping Feature
Background:
	Given the user navigates to recipe page

  Scenario: Extract Recipe Titles
    Given the user extracts recipe titles
    When the user verifies the extracted titles
    
#Scenario: Extract Recipe links from Health Category
#		Given the user navigates to Health recipe page
#		