var shown = false;
var scrollThreshold = 10; // Set a threshold for comparison

$(window).scroll(function() {
  if ($(window).scrollTop() <= scrollThreshold) { // Use <= for comparison
    $('.menu').hide();
    $('.msg').fadeIn(200);
    $('.welcome').animate({
      height: "220px"
    }, 300);
    shown = false;
  } else if ($(window).scrollTop() > scrollThreshold && !shown) { // Use > for comparison
    $('.menu').fadeIn(200);
    $('.msg').hide();
    $('.welcome').animate({
      height: "50px"
    }, 300);
    shown = true;
  }
});
