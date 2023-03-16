#!/usr/bin/env bash
set -Eeou pipefail

script_dir="$(cd "$(dirname "${BASH_SOURCE[0]}")" &>/dev/null && pwd -P)"
echo "Script Dir: $script_dir"
root_dir="$(dirname "$script_dir")"
echo "Root Dir: $root_dir"
script_name="$(basename "${BASH_SOURCE[0]}")"
echo "Script Name: $script_name"
echo

build_type_option="-b|--build-type"
script_options=(
  "build_type" "$build_type_option" 1 1 "Build Type"
  "print_only" "-p|--print-only" 0 0 "Print commands and do not execute them"
  "print_help" "-h|--help" 0 0 "Print help menu"
)
source "$script_dir/util.sh"
source "$script_dir/analyze.sh"
[ ${print_help-0} -eq 1 ] && { print_usage; exit 0; }

[ -z "${build_type-}" ] && missing_value "$build_type_option"
unset build_type_option

build_type="$(tr '[:lower:]' '[:upper:]' <<< ${build_type:0:1})${build_type:1}"
build_variant=$build_type
build="$(tr '[:lower:]' '[:upper:]' <<< ${build_variant:0:1})${build_variant:1}"
prefix="refs/heads/"
print_only=${print_only-0}

script_values=(
  "Build Type" "$(tr '[:upper:]' '[:lower:]' <<< ${build_type:0:1})${build_type:1}"
  "Build Variant" "$build_variant"
  "Build" "$build"
  "Print Only" "$print_only"
)
print_parameters "${script_values[@]}"
unset script_values

assemble() {
  echo "Building Variant: $build_variant ..."
  resolve "$root_dir/gradlew clean assemble$build"
  print_done
}

symbols() {
  echo "Copying Symbols ..."

  local target="$script_dir/symbols"
  resolve "mkdir -p $target"

  local dirs=(
    "$root_dir/app/build/outputs/mapping/$build_variant"
    "$root_dir/app/build/outputs/native-debug-symbols/$build_variant"
  )
  for source in ${dirs[@]}
  do
    [ -d $source ] && resolve "cp -r $source/* $target"
  done

  print_done
}

resolve "$root_dir/gradlew clean"
echo

ktlint
detekt
assemble
symbols
