#!/usr/bin/env bash
set -Eeou pipefail

#script_options=(
#  #variable options required has_value description
#  "value" "-v|--value" 1 1 "required option with value"
#  "eulav" "-e|--eulav" 1 0 "required option without value"
#  "flag" "-f|--flag" 0 1 "optional option with value"
#  "galf" "-g|--glaf" 0 0 "optional option without value"
#)
#print_parameters "${script_options[@]}"

script_option_size=5
script_options_size=${#script_options[@]}

print_usage() {
  echo "Usage: $script_name {options}"
  for (( i=0; i<script_options_size; i+=$script_option_size ))
  do
    local prefix=""
    if [ ${script_options[i + 2]} -eq 1 ]; then prefix="(Required) "; else prefix="(Optional) "; fi

    local suffix=""
    if [ ${script_options[i + 3]} -eq 1 ]; then suffix=" {${script_options[i]}}"; fi

    echo "${prefix}${script_options[i + 1]}${suffix} - ${script_options[i + 4]}"
  done
  echo
}

missing_value() {
  echo "$1 required"
  print_usage
  exit 1
}

print_parameters() {
  echo "Parameters"
  local parameters=("$@")
  local parameters_size=${#parameters[@]}
  for (( i=0; i<parameters_size; i+=2 ))
  do
    echo "${parameters[i]}: ${parameters[i + 1]}"
  done
  echo
}

resolve() {
  local cmd="$1"
  echo "$cmd"
  if [ $print_only -ne 1 ]
  then
    eval "$cmd"
  fi
}

print_done() {
  echo "Done ..."
  echo
}

# Check array contains value
# [[ "${array[@]}" =~ "$value" ]]

while [[ $# -gt 0 ]]
do
  option="$1"

  index=-1
  for (( i=0; i<script_options_size; i+=$script_option_size ))
  do
    options="${script_options[i + 1]}"
    short="${options#*|}"
    long="${options%|*}"

    if [ "$option" == "$short" ] || [ "$option" == "$long" ]
    then
      index=$((i / script_option_size))
      break
    fi
  done
  index=$((index * script_option_size))

  if [ $index -gt -1 ]
  then
    variable="${script_options[index]}"
    has_value=${script_options[index + 3]}
    shift

    if [ $has_value -eq 1 ]
    then
      [ $# -lt 1 ] && missing_value "$options"

      value="$1"
      eval "$variable=$value"
      shift
    else
      eval "$variable=1"
    fi
  else
    # TODO: Print unrecognized option and exit or just ignore ???
    shift
  fi

  unset option
  unset index
  unset options
  unset short
  unset long
  unset variable
  unset has_value
  unset value
done
