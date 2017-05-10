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
    
    
    
Comment
    Wait Until Page Contains    Admin   timeout=${FIVESECOND}
    Input Text  dom=document.getElementsByClassName("input is-medium is-borderless")[0]    Somkiat
    Click Link    dom=document.getElementsByClassName("footer-item icon is-medium right")[0]
    Page Should Contain     Somkiat
    
    
    Wait Until Page Contains    Admin   timeout=${FIVESECOND}
    Input Text  dom=document.getElementsByClassName("input is-medium is-borderless")[1]    Pichai
    Click Link    dom=document.getElementsByClassName("footer-item icon is-medium right")[1]
    Page Should Contain     Pichai



Search
    Wait Until Page Contains    Admin   timeout=${FIVESECOND}
    Input Text  dom=document.getElementsByTagName("input")[0]   meranote
    Wait Until Element Is Visible   dom=document.getElementsByClassName("search")  timeout=${FIVESECOND}
    Click Link    dom=document.links[1]
    Page Should Contain     meranote
    
View Post
    Wait Until Page Contains    Test update   timeout=${FIVESECOND}
    Click Link    dom=document.getElementsByTagName("a")[document.getElementsByTagName("a").length-1]
    Page Should Contain     ;w;
    

    