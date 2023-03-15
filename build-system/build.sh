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

ktlint() {
  echo "Executing Android Lint ..."
  resolve "$root_dir/gradlew --no-configuration-cache --no-build-cache :app:ktlintFormat"

  print_done
}

detekt() {
  echo "Downloading detekt ..."
#
#  local detekt_dir="$script_dir/detekt"
  local detekt_version="1.22.0"
#  local detekt_url="https://github.com/detekt/detekt/releases/download/v$detekt_version"
#  local detekt_cli="detekt-cli-$detekt_version"
#  local detekt_formatting="detekt-formatting-$detekt_version.jar"
#  local detekt_cli_zip="$detekt_cli.zip"
#
#  resolve "rm -rf $detekt_dir"
#  resolve "curl -JLO $detekt_url/$detekt_cli_zip"
#  resolve "unzip $detekt_cli_zip -d $script_dir"
#  resolve "rm $detekt_cli_zip"
#  resolve "mv $script_dir/$detekt_cli $detekt_dir"
#  resolve "curl -JLO $detekt_url/$detekt_formatting"
#  resolve "mv $detekt_formatting $detekt_dir/lib"

  echo "Executing detekt ..."

  local cmd="$script_dir/detekt/bin/detekt-cli"
  cmd="${cmd} --build-upon-default-config"
  cmd="${cmd} --config $root_dir/.analysis/detekt.yml"
  cmd="${cmd} --jvm-target 1.8"
  cmd="${cmd} --language-version 1.4"
  cmd="${cmd} --plugins $script_dir/detekt/lib/detekt-formatting-1.22.0.jar"
  cmd="${cmd} --report html:$root_dir/.analysis/detekt.html"
  cmd="${cmd} --input $root_dir/app/src/"
  resolve "$cmd"

  print_done
}

assemble() {
  echo "Building Variant: $build_variant ..."
  # TODO: Correct command to build here (apk or bundle)
  resolve "$root_dir/gradlew clean assemble$build"
  print_done
}

symbols() {
  echo "Copying Symbols ..."

  local target="$script_dir/symbols"
  resolve "mkdir -p $target"

  local dirs=(
    "$script_dir/app/build/outputs/mapping/$build_variant"
    "$script_dir/app/build/outputs/native-debug-symbols/$build_variant"
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
#detekt
#assemble
#symbols
