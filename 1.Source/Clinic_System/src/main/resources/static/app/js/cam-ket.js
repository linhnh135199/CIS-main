function printCamKet() {
     var printContents = document.getElementById('view-print-cam-ket').innerHTML;
     var originalContents = document.body.innerHTML;

     document.body.innerHTML = printContents;

     window.print();

     document.body.innerHTML = originalContents;
}