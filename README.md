# digital-sign-pdf
Digitally Signing a PDF using Java (iTextPDF). 

Create a Runnable jar and run the command below - Make sure to change the path and use sample pfx and example pdf for testing purposes - Happy Coding  
```
java -jar "Signature.java" "PFX=/pathTo/lib/apples.pfx;PASSPFX=emudhra;SOURCE=/pathTo/lib/example.pdf;TARGET=/pathTo/lib/signedPDF-example.pdf;VISIBLE=true;PAGE=1;POSITION=0;REASON=I am the author of this document;LOCATION=Bangalore;URLIMAGE=/pathTo/lib/logo.png;MARGINLEFT=415.0F;MARGINBOTTOM=180.0F"
```
