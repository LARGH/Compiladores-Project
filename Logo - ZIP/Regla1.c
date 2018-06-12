function regla(){
  if($1  == 0){
    FORWARD[$2];
    return 0;
  }
  if($2  <= 0){
    return 0;
  }
  regla($1 - 1 , $2 - (0.25)*($2));
  TURN[90];
  FORWARD[$2 - (0.25)*($2)];
  TURN[-180];
  FORWARD[$2 - (0.25)*($2)];
  TURN[90];
  FORWARD[1];
  regla($1 - 1 , $2 - (0.25)*($2));
}
regla(3,30);
