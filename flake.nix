{
  description = "Minimal example of building Kotlin with Gradle and Nix";

  inputs = {
    nixpkgs.url = "github:nixos/nixpkgs?ref=nixos-unstable";
  };

  outputs =
    { self, nixpkgs }:
    let
      system = "x86_64-linux";
      pkgs = import nixpkgs { inherit system; };
      updateLocks = pkgs.callPackage ./update-locks.nix { };
    in
    {
      devShells.${system}.default = pkgs.mkShell {
        buildInputs = [
          pkgs.gradle_8
          pkgs.temurin-bin-21
          updateLocks
          pkgs.ktlint
        ];
      };
      packages.${system}.default = pkgs.callPackage ./build.nix { };
    };
}
