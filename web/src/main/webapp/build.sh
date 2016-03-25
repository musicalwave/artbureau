cd src

for file in *.js
do
    echo building $file
    browserify -t [ babelify --presets [ react es2015 ] ] $file -o ../resources/js/$file
    minify ../resources/js/$file
    echo $file built
done

echo 'build completed'

