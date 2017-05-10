*** Settings ***
Library   Selenium2Library
Library  Collections

*** Variables ***
${HOMEPAGE}    https://shenzhentagram-d1a0b.firebaseapp.com
${BROWSER}    chrome
${FIVESECOND}    5.0
${TENSECOND}    10.0
${USERNAME}    admin
${PASSWORD}    password

*** Keywords ***
Go to index
    ${chrome_options} =     Evaluate    sys.modules['selenium.webdriver'].ChromeOptions()    sys, selenium.webdriver

    ${prefs}    Create Dictionary   credentials_enable_service=${false}  

    Call Method    ${chrome_options}    add_experimental_option    prefs    ${prefs}
    Call Method    ${chrome_options}    add_argument    --disable-infobars 
    Call Method     ${chrome_options}  add_argument  --start-maximized 
    Call Method     ${chrome_options}  add_argument  --ignore-certificate-errors
    Create WebDriver    Chrome    chrome_options=${chrome_options}
    Go to    ${HOMEPAGE}
    Set Selenium Speed  0.7

*** Test Case ***

Login
    Go to index
    Input Text  dom=document.getElementsByTagName("input")[1]   ${USERNAME}
    Input Password  dom=document.getElementsByTagName("input")[2]   ${PASSWORD}
    Click Button    dom=document.getElementsByClassName("button is-primary is-medium")
    Page Should Contain     Admin
        
Reaction Post
    Wait Until Page Contains    Admin   timeout=${FIVESECOND}
    Click Link    dom=document.getElementsByClassName("footer-item icon is-medium left")[0]
    Wait Until Element Is Visible   dom=document.getElementsByClassName("button is-large is-borderless is-marginless")  timeout=${FIVESECOND}
    Click Link    dom=document.getElementsByClassName("button is-large is-borderless is-marginless")[1]
    
    