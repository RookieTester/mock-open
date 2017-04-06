#$1 filename
#$2 json
#echo ''>>/usr/share/nginx/html/$1.json
rm -f /usr/share/nginx/html/$1.json
echo "$2">>/usr/share/nginx/html/$1.json
