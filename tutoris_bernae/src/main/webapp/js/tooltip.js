$(document).ready(function () {
  $("span.hint").hover(function () {
    $(this).append('<div class="tooltip"><p>'+"Password between 8-14 characters. At least 1 uppercase letter, 1 digit, 1 special character."+'</p></div>');
  }, function () {
    $("div.tooltip").remove();
  });
});