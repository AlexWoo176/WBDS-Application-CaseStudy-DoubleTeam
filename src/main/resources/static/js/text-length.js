// $(function () {
//     let limitW = 150;
//     let char = 6;
//     let txt = $('#content1').html();
//     let txtStart = txt.slice(0, limitW).replace(/\w+$/, '');
//     let txtEnd = txt.slice(txtStart.length);
//     let urlIdFist = "/lesson/showlesson/" + $('#id1').html();
//     if (txtEnd.replace(/\s+$/, '').split(' ').length > char) {
//         $('#content1').html([
//                 txtStart,
//                 '<a style="color: #BA4046; text-decoration: none" href='+ urlIdFist+ '>...xem thêm</a>',
//                 '<span class="detail">',
//                 txtEnd,
//                 '</span>'
//             ].join('')
//         );
//     }
//     $('span.detail').hide();
// });